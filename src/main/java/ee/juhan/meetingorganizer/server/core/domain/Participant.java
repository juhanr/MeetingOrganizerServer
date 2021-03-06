package ee.juhan.meetingorganizer.server.core.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ee.juhan.meetingorganizer.server.rest.domain.MapLocation;
import ee.juhan.meetingorganizer.server.rest.domain.ParticipantDto;
import ee.juhan.meetingorganizer.server.rest.domain.ParticipationAnswer;
import ee.juhan.meetingorganizer.server.rest.domain.SendGpsLocationAnswer;

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
	private ParticipationAnswer participationAnswer = ParticipationAnswer.NO_ANSWER;

	@Column(nullable = false)
	private SendGpsLocationAnswer sendGpsLocationAnswer = SendGpsLocationAnswer.NO_ANSWER;

	private MapLocation mapLocation;

	@Temporal(TemporalType.TIMESTAMP)
	private Date locationTimestamp;

	protected Participant() {}

	public Participant(Account account, Meeting meeting, String name, String email,
			String phoneNumber, ParticipationAnswer participationAnswer,
			SendGpsLocationAnswer sendGpsLocationAnswer, MapLocation mapLocation,
			Date locationTimestamp) {
		this.account = account;
		this.meeting = meeting;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.participationAnswer = participationAnswer;
		this.sendGpsLocationAnswer = sendGpsLocationAnswer;
		this.mapLocation = mapLocation;
		setLocationTimestamp(locationTimestamp);
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

	public SendGpsLocationAnswer getSendGPSLocationAnswer() {
		return sendGpsLocationAnswer;
	}

	public void setSendGPSLocationAnswer(SendGpsLocationAnswer sendGpsLocationAnswer) {
		this.sendGpsLocationAnswer = sendGpsLocationAnswer;
	}

	public final MapLocation getMapLocation() {
		return mapLocation;
	}

	public final void setMapLocation(MapLocation mapLocation) {
		this.mapLocation = mapLocation;
	}

	public Date getLocationTimestamp() {
		return (Date) locationTimestamp.clone();
	}

	public void setLocationTimestamp(Date locationTimestamp) {
		this.locationTimestamp =
				locationTimestamp != null ? (Date) locationTimestamp.clone() : null;
	}

	public final ParticipantDto toDTO() {
		int accountId = account == null ? 0 : account.getId();
		int meetingId = meeting == null ? 0 : meeting.getId();
		return new ParticipantDto(id, accountId, meetingId, name, email, phoneNumber,
				participationAnswer, sendGpsLocationAnswer, mapLocation, locationTimestamp);
	}

}