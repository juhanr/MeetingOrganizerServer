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

	public final int getId() {
        return id;
    }

    public final void setId(int id) {
        this.id = id;
    }

    public final int getAccountId() {
        return accountId;
    }

    public final void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public final String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final String getEmail() {
        return email;
    }

    public final void setEmail(String email) {
        this.email = email;
    }

    public final String getPhoneNumber() {
        return phoneNumber;
    }

    public final void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public final ParticipationAnswer getParticipationAnswer() {
        return participationAnswer;
    }

    public final void setParticipationAnswer(ParticipationAnswer participationAnswer) {
        this.participationAnswer = participationAnswer;
    }

    public final MapCoordinate getLocation() {
        return location;
    }

    public final void setLocation(MapCoordinate location) {
        this.location = location;
    }
    
}
