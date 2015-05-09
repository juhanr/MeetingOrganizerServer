package ee.juhan.meetingorganizer.server.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import ee.juhan.meetingorganizer.server.rest.domain.MapCoordinate;
import ee.juhan.meetingorganizer.server.rest.domain.ParticipantDTO;
import ee.juhan.meetingorganizer.server.rest.domain.ParticipationAnswer;

@Entity
public class Participant implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int accountId;

	@Column(nullable = false)
	private String name;

	private String email;

	@Column(nullable = false)
	private String phoneNumber;

	@Column(nullable = false)
	private ParticipationAnswer participationAnswer = ParticipationAnswer.NOT_ANSWERED;

	private MapCoordinate location;

	protected Participant() {
		super();
	}

	public Participant(int accountId, String name, String email,
			String phoneNumber, ParticipationAnswer participationAnswer,
			MapCoordinate location) {
		super();
		this.accountId = accountId;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.participationAnswer = participationAnswer;
		this.location = location;
	}

	public Participant(int accountId, String name, String email,
			String phoneNumber) {
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

	public Participant(String name, String email, String phoneNumber) {
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
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

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setParticipationAnswer(ParticipationAnswer participationAnswer) {
		this.participationAnswer = participationAnswer;
	}

	public void setLocation(MapCoordinate location) {
		this.location = location;
	}

	public ParticipantDTO toDTO() {
		return new ParticipantDTO(id, accountId, name, email, phoneNumber,
				participationAnswer, location);
	}

	public Participant updateInfo(ParticipantDTO participantDTO) {
		this.participationAnswer = participantDTO.getParticipationAnswer();
		this.location = participantDTO.getLocation();
		return this;
	}

}