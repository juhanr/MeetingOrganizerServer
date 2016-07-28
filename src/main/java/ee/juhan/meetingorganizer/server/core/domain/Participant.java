package ee.juhan.meetingorganizer.server.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import ee.juhan.meetingorganizer.server.rest.domain.MapCoordinate;
import ee.juhan.meetingorganizer.server.rest.domain.ParticipantDTO;
import ee.juhan.meetingorganizer.server.rest.domain.ParticipationAnswer;

@Entity
public class Participant implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	private Account account;

	@ManyToOne
	private Meeting meeting;

	private String name;

	private String email;

	@Column(nullable = false)
	private String phoneNumber;

	@Column(nullable = false)
	private ParticipationAnswer participationAnswer = ParticipationAnswer.NOT_ANSWERED;

	private MapCoordinate location;

	protected Participant() {}

	public Participant(Account account, Meeting meeting, String name, String email,
			String phoneNumber, ParticipationAnswer participationAnswer, MapCoordinate location) {
		this.account = account;
		this.meeting = meeting;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.participationAnswer = participationAnswer;
		this.location = location;
	}

	public Participant(Meeting meeting, String name, String email, String phoneNumber) {
		this.meeting = meeting;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

	public final int getId() {
		return id;
	}

	public final Account getAccount() {
		return account;
	}

	public final void setAccount(Account account) {
		this.account = account;
	}

	public final Meeting getMeeting() {
		return meeting;
	}

	public final void setMeeting(Meeting meeting) {
		this.meeting = meeting;
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

	public final ParticipantDTO toDTO() {
		int accountId = account == null ? 0 : account.getId();
		int meetingId = meeting == null ? 0 : meeting.getId();
		return new ParticipantDTO(id, accountId, meetingId, name, email, phoneNumber,
				participationAnswer, location);
	}

	public final Participant updateInfo(ParticipantDTO participantDTO) {
		this.participationAnswer = participantDTO.getParticipationAnswer();
		this.location = participantDTO.getLocation();
		return this;
	}

}