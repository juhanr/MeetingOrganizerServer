package ee.juhan.meetingorganizer.server.rest.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import ee.juhan.meetingorganizer.server.core.util.JsonDateDeserializer;

public class MeetingDTO {

	private int leaderId;
	private String title;
	private String description;
	private Date startDateTime;
	private Date endDateTime;
	private double locationLatitude;
	private double locationLongitude;
	private LocationType locationType;
	private Set<ParticipantDTO> participants = new HashSet<>();

	public MeetingDTO() {

	}

	public MeetingDTO(int leaderId, String title, String description,
			Date startDateTime, Date endDateTime, double locationLatitude,
			double locationLongitude, LocationType locationType) {
		this.leaderId = leaderId;
		this.title = title;
		this.description = description;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.locationLatitude = locationLatitude;
		this.locationLongitude = locationLongitude;
		this.locationType = locationType;
	}

	public int getLeaderId() {
		return leaderId;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Date getStartDateTime() {
		return startDateTime;
	}

	public Date getEndDateTime() {
		return endDateTime;
	}

	public double getLocationLatitude() {
		return locationLatitude;
	}

	public double getLocationLongitude() {
		return locationLongitude;
	}

	public LocationType getLocationType() {
		return locationType;
	}

	public Set<ParticipantDTO> getParticipants() {
		return participants;
	}

	public void setLeaderId(int leaderId) {
		this.leaderId = leaderId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonDeserialize(using = JsonDateDeserializer.class)
	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}

	@JsonDeserialize(using = JsonDateDeserializer.class)
	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}

	public void setLocationLatitude(double locationLatitude) {
		this.locationLatitude = locationLatitude;
	}

	public void setLocationLongitude(double locationLongitude) {
		this.locationLongitude = locationLongitude;
	}

	public void setLocationType(LocationType locationType) {
		this.locationType = locationType;
	}

	public void setParticipants(Set<ParticipantDTO> participants) {
		this.participants = participants;
	}

	public boolean addParticipant(ParticipantDTO participant) {
		return participants.add(participant);
	}

}
