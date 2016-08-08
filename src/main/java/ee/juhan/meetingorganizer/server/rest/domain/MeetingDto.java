package ee.juhan.meetingorganizer.server.rest.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ee.juhan.meetingorganizer.server.util.JsonDateDeserializer;

public class MeetingDto {

	private int id;
	private int leaderId;
	private String title;
	private String description;
	private Date startDateTime;
	private Date endDateTime;
	private MapLocation mapLocation;
	private LocationChoice locationChoice;
	private List<ParticipantDto> participants = new ArrayList<>();
	private List<MapLocation> userPreferredLocations = new ArrayList<>();
	private MeetingStatus status;

	public MeetingDto() {}

	public MeetingDto(int id, int leaderId, String title, String description, Date startDateTime,
			Date endDateTime, MapLocation mapLocation, LocationChoice locationChoice,
			List<MapLocation> userPreferredLocations, MeetingStatus status) {
		this.id = id;
		this.leaderId = leaderId;
		this.title = title;
		this.description = description;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.mapLocation = mapLocation;
		this.locationChoice = locationChoice;
		this.userPreferredLocations = userPreferredLocations;
		this.status = status;
	}

	public final int getId() {
		return id;
	}

	public final void setId(int id) {
		this.id = id;
	}

	public final int getLeaderId() {
		return leaderId;
	}

	public final void setLeaderId(int leaderId) {
		this.leaderId = leaderId;
	}

	public final String getTitle() {
		return title;
	}

	public final void setTitle(String title) {
		this.title = title;
	}

	public final String getDescription() {
		return description;
	}

	public final void setDescription(String description) {
		this.description = description;
	}

	public final Date getStartDateTime() {
		return (Date) startDateTime.clone();
	}

	@JsonDeserialize(using = JsonDateDeserializer.class)
	public final void setStartDateTime(Date startTime) {
		this.startDateTime = (Date) startTime.clone();
	}

	public final Date getEndDateTime() {
		return (Date) endDateTime.clone();
	}

	@JsonDeserialize(using = JsonDateDeserializer.class)
	public final void setEndDateTime(Date endTime) {
		this.endDateTime = (Date) endTime.clone();
	}

	public final MapLocation getMapLocation() {
		return mapLocation;
	}

	public final void setMapLocation(MapLocation mapLocation) {
		this.mapLocation = mapLocation;
	}

	public final LocationChoice getLocationChoice() {
		return locationChoice;
	}

	public final void setLocationChoice(LocationChoice locationChoice) {
		this.locationChoice = locationChoice;
	}

	public final List<ParticipantDto> getParticipants() {
		return participants;
	}

	public final void setParticipants(List<ParticipantDto> participants) {
		this.participants = participants;
	}

	public final boolean addParticipant(ParticipantDto participant) {
		return participants.add(participant);
	}

	public final List<MapLocation> getUserPreferredLocations() {
		return userPreferredLocations;
	}

	public final void setUserPreferredLocations(List<MapLocation> userPreferredLocations) {
		this.userPreferredLocations = userPreferredLocations;
	}

	public final void addUserPreferredLocation(MapLocation userPreferredLocation) {
		this.userPreferredLocations.add(userPreferredLocation);
	}

	public final void removeUserPreferredLocation(MapLocation userPreferredLocation) {
		this.userPreferredLocations.remove(userPreferredLocation);
	}

	public MeetingStatus getStatus() {
		return status;
	}

	public void setStatus(MeetingStatus status) {
		this.status = status;
	}

}
