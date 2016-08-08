package ee.juhan.meetingorganizer.server.rest.domain;

import java.io.Serializable;

public class MapLocation implements Serializable {

	private static final long serialVersionUID = 1L;

	private double latitude;
	private double longitude;
	private String address;
	private String placeName;
	private String[] placeTypes;

	public MapLocation() {}

	public MapLocation(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String[] getPlaceTypes() {
		return placeTypes;
	}

	public void setPlaceTypes(String[] placeTypes) {
		this.placeTypes = placeTypes;
	}

}
