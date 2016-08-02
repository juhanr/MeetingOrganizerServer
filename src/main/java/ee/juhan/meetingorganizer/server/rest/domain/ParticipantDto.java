package ee.juhan.meetingorganizer.server.rest.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Date;

import ee.juhan.meetingorganizer.server.util.JsonDateDeserializer;

public class ParticipantDto {

	private int id;
	private int accountId;
	private int meetingId;
	private String name;
	private String email;
	private String phoneNumber;
	private ParticipationAnswer participationAnswer = ParticipationAnswer.NO_ANSWER;
	private SendGpsLocationAnswer sendGpsLocationAnswer = SendGpsLocationAnswer.NO_ANSWER;
	private MapCoordinate location;
	private Date locationTimestamp;

	public ParticipantDto() {}

	public ParticipantDto(int id, int accountId, int meetingId, String name, String email,
			String phoneNumber, ParticipationAnswer participationAnswer,
			SendGpsLocationAnswer sendGpsLocationAnswer, MapCoordinate location,
			Date locationTimestamp) {
		this.id = id;
		this.accountId = accountId;
		this.meetingId = meetingId;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.participationAnswer = participationAnswer;
		this.sendGpsLocationAnswer = sendGpsLocationAnswer;
		this.location = location;
		this.locationTimestamp = locationTimestamp;
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

	public final int getMeetingId() {
		return meetingId;
	}

	public final void setMeetingId(int meetingId) {
		this.meetingId = meetingId;
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

	public SendGpsLocationAnswer getSendGpsLocationAnswer() {
		return sendGpsLocationAnswer;
	}

	public void setSendGpsLocationAnswer(SendGpsLocationAnswer sendGpsLocationAnswer) {
		this.sendGpsLocationAnswer = sendGpsLocationAnswer;
	}

	public final MapCoordinate getLocation() {
		return location;
	}

	public final void setLocation(MapCoordinate location) {
		this.location = location;
	}

	public Date getLocationTimestamp() {
		return locationTimestamp;
	}

	@JsonDeserialize(using = JsonDateDeserializer.class)
	public void setLocationTimestamp(Date locationTimestamp) {
		this.locationTimestamp = locationTimestamp;
	}
}
