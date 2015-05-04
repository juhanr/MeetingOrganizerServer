package ee.juhan.meetingorganizer.server.core.util;

import java.util.ArrayList;
import java.util.Set;

import ee.juhan.meetingorganizer.server.core.domain.Meeting;
import ee.juhan.meetingorganizer.server.core.domain.Participant;
import ee.juhan.meetingorganizer.server.rest.domain.LocationType;
import ee.juhan.meetingorganizer.server.rest.domain.MapCoordinate;

public class LocationGeneratorUtil {

	public static MapCoordinate findOptimalLocation(Meeting meeting) {
		MapCoordinate centerCoordinate = getCenterCoordinate(meeting);
		if (meeting.getLocationType() == LocationType.GENERATED_FROM_PREDEFINED_LOCATIONS) {
			return getNearestLocation(centerCoordinate,
					meeting.getPredefinedLocations());
		}
		return centerCoordinate;
	}

	private static MapCoordinate getCenterCoordinate(Meeting meeting) {
		ArrayList<Double> xCoordinates = new ArrayList<Double>();
		ArrayList<Double> yCoordinates = new ArrayList<Double>();
		ArrayList<Double> zCoordinates = new ArrayList<Double>();

		// Convert lat/lon to Cartesian coordinates for each location.
		for (Participant participant : meeting.getParticipants()) {
			if (participant.getLocation() != null) {
				Double latitude = Math.toRadians(participant.getLocation()
						.getLatitude());
				Double longitude = Math.toRadians(participant.getLocation()
						.getLongitude());
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
		Double smallestDistance = 100000.0;
		for (MapCoordinate location : locations) {
			Double distance = getDistance(coordinate, location);
			if (distance < smallestDistance) {
				smallestDistance = distance;
				nearestLocation = location;
			}
		}
		return nearestLocation;
	}

	private static Double getDistance(MapCoordinate coord1, MapCoordinate coord2) {
		final int earthRadius = 6371;
		Double dLat = Math.toRadians(coord2.getLatitude()
				- coord1.getLatitude());
		Double dLon = Math.toRadians(coord2.getLongitude()
				- coord1.getLongitude());
		Double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.cos(Math.toRadians(coord1.getLatitude()))
				* Math.cos(Math.toRadians(coord2.getLatitude()))
				* Math.sin(dLon / 2) * Math.sin(dLon / 2);
		Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return earthRadius * c;
	}

}