package ee.juhan.meetingorganizer.server.rest.domain;

public class ParticipantDTO {

	private String name;
	private String email;
	private String phoneNumber;
	private boolean isParticipating;
	private double locationLatitude;
	private double locationLongitude;

	public ParticipantDTO() {
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public boolean isParticipating() {
		return isParticipating;
	}

	public double getLocationLatitude() {
		return locationLatitude;
	}

	public double getLocationLongitude() {
		return locationLongitude;
	}

}
