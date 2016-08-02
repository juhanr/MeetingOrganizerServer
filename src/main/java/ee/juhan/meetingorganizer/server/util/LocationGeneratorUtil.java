package ee.juhan.meetingorganizer.server.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ee.juhan.meetingorganizer.server.core.domain.Meeting;
import ee.juhan.meetingorganizer.server.core.domain.Participant;
import ee.juhan.meetingorganizer.server.rest.domain.LocationType;
import ee.juhan.meetingorganizer.server.rest.domain.MapCoordinate;

public final class LocationGeneratorUtil {

	private LocationGeneratorUtil() {}

	public static MapCoordinate findOptimalLocation(Meeting meeting,
			List<Participant> participants) {
		MapCoordinate centerCoordinate = getCenterCoordinate(meeting, participants);
		if (meeting.getLocationType() == LocationType.GENERATED_FROM_PREFERRED_LOCATIONS) {
			return getNearestLocation(centerCoordinate, meeting.getUserPreferredLocations());
		}
		return centerCoordinate;
	}

	private static MapCoordinate getCenterCoordinate(Meeting meeting,
			List<Participant> participants) {
		ArrayList<Double> xCoordinates = new ArrayList<>();
		ArrayList<Double> yCoordinates = new ArrayList<>();
		ArrayList<Double> zCoordinates = new ArrayList<>();

		// Convert lat/lon to Cartesian coordinates for each location.
		for (Participant participant : participants) {
			if (participant.getLocation() != null) {
				Double latitude = Math.toRadians(participant.getLocation().getLatitude());
				Double longitude = Math.toRadians(participant.getLocation().getLongitude());
				xCoordinates.add(Math.cos(latitude) * Math.cos(longitude));
				yCoordinates.add(Math.cos(latitude) * Math.sin(longitude));
				zCoordinates.add(Math.sin(latitude));
			}
		}

		// Compute average x, y and z coordinates.
		Double xSum = 0.0;
		Double ySum = 0.0;
		Double zSum = 0.0;

		for (int i = 0; i < xCoordinates.size(); i++) {
			xSum += xCoordinates.get(i);
			ySum += yCoordinates.get(i);
			zSum += zCoordinates.get(i);
		}

		Double xAverage = xSum / xCoordinates.size();
		Double yAverage = ySum / yCoordinates.size();
		Double zAverage = zSum / zCoordinates.size();

		// Convert average x, y, z coordinate to latitude and longitude.
		Double hyp = Math.sqrt(xAverage * xAverage + yAverage * yAverage);
		Double latitude = Math.toDegrees(Math.atan2(zAverage, hyp));
		Double longitude = Math.toDegrees(Math.atan2(yAverage, xAverage));
		return new MapCoordinate(latitude, longitude);
	}

	private static MapCoordinate getNearestLocation(MapCoordinate coordinate,
			Set<MapCoordinate> locations) {
		MapCoordinate nearestLocation = null;
		Double smallestDistance = Double.POSITIVE_INFINITY;
		for (MapCoordinate location : locations) {
			Double distance = getDistance(coordinate, location);
			if (distance < smallestDistance) {
				smallestDistance = distance;
				nearestLocation = location;
			}
		}
		return nearestLocation;
	}

	private static Double getDistance(MapCoordinate coordinate1, MapCoordinate coordinate2) {
		final int earthRadius = 6371;
		Double dLat = Math.toRadians(coordinate2.getLatitude() - coordinate1.getLatitude());
		Double dLon = Math.toRadians(coordinate2.getLongitude() - coordinate1.getLongitude());
		Double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
				Math.cos(Math.toRadians(coordinate1.getLatitude())) *
						Math.cos(Math.toRadians(coordinate2.getLatitude())) * Math.sin(dLon / 2) *
						Math.sin(dLon / 2);
		Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return earthRadius * c;
	}

}