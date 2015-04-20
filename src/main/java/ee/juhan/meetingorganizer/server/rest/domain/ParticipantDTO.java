package ee.juhan.meetingorganizer.server.rest.domain;

public class ParticipantDTO {

	private int accountId;
	private String name;
	private String email;
	private String phoneNumber;
	private ParticipationAnswer participationAnswer = ParticipationAnswer.NOT_ANSWERED;
	private double locationLatitude;
	private double locationLongitude;

	public ParticipantDTO() {

	}

	public ParticipantDTO(int accountId, String name, String email,
			String phoneNumber, ParticipationAnswer participationAnswer,
			double locationLatitude, double locationLongitude) {
		this.accountId = accountId;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.participationAnswer = participationAnswer;
		this.locationLatitude = locationLatitude;
		this.locationLongitude = locationLongitude;
	}

	public int getAccountId() {
		return accountId;
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

	public ParticipationAnswer getParticipationAnswer() {
		return participationAnswer;
	}

	public double getLocationLatitude() {
		return locationLatitude;
	}

	public double getLocationLongitude() {
		return locationLongitude;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setParticipationAnswer(ParticipationAnswer participationAnswer) {
		this.participationAnswer = participationAnswer;
	}

	public void setLocationLatitude(double locationLatitude) {
		this.locationLatitude = locationLatitude;
	}

	public void setLocationLongitude(double locationLongitude) {
		this.locationLongitude = locationLongitude;
	}

}
