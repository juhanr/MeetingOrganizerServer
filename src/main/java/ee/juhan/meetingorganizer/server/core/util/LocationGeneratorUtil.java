package ee.juhan.meetingorganizer.server.core.util;

import java.util.ArrayList;

import ee.juhan.meetingorganizer.server.core.domain.Meeting;
import ee.juhan.meetingorganizer.server.core.domain.Participant;
import ee.juhan.meetingorganizer.server.rest.domain.MapCoordinate;

public class LocationGeneratorUtil {

	public static MapCoordinate findOptimalLocation(Meeting meeting) {
		return getCenterCoordinate(meeting);
	}

	private static MapCoordinate getCenterCoordinate(Meeting meeting) {
		ArrayList<Double> xCoordinates = new ArrayList<Double>();
		ArrayList<Double> yCoordinates = new ArrayList<Double>();
		ArrayList<Double> zCoordinates = new ArrayList<Double>();

		// Convert lat/lon to Cartesian coordinates for each location.
		for (Participant participant : meeting.getParticipants()) {
			if (participant.getLocation() != null) {
				Double latitude = participant.getLocation().getLatitude()
						* Math.PI / 180.0;
				Double longitude = participant.getLocation().getLongitude()
						* Math.PI / 180.0;
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
		Double latitude = Math.atan2(zAverage, hyp);
		Double longitude = Math.atan2(yAverage, xAverage);
		return new MapCoordinate(latitude, longitude);
	}

}