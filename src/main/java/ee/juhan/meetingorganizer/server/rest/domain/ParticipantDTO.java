package ee.juhan.meetingorganizer.server.rest.domain;

public class ParticipantDTO {

	private int id;
	private int accountId;
	private String name;
	private String email;
	private String phoneNumber;
	private ParticipationAnswer participationAnswer = ParticipationAnswer.NOT_ANSWERED;
	private MapCoordinate location;

	public ParticipantDTO() {

	}

	public ParticipantDTO(int id, int accountId, String name, String email,
			String phoneNumber, ParticipationAnswer participationAnswer,
			MapCoordinate location) {
		this.id = id;
		this.accountId = accountId;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.participationAnswer = participationAnswer;
		this.location = location;
	}

	public int getId() {
		return id;
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

	public MapCoordinate getLocation() {
		return location;
	}

	public void setId(int id) {
		this.id = id;
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

	public void setLocation(MapCoordinate location) {
		this.location = location;
	}

}
