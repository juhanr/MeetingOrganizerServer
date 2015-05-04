package ee.juhan.meetingorganizer.server.rest.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import ee.juhan.meetingorganizer.server.core.util.JsonDateDeserializer;

public class MeetingDTO {

	private int id;
	private int leaderId;
	private String title;
	private String description;
	private Date startDateTime;
	private Date endDateTime;
	private MapCoordinate location;
	private LocationType locationType;
	private ArrayList<ParticipantDTO> participants = new ArrayList<>();
	private HashSet<MapCoordinate> predefinedLocations = new HashSet<>();

	public MeetingDTO() {

	}

	public MeetingDTO(int id, int leaderId, String title, String description,
			Date startDateTime, Date endDateTime, MapCoordinate location,
			LocationType locationType) {
		this.id = id;
		this.leaderId = leaderId;
		this.title = title;
		this.description = description;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.location = location;
		this.locationType = locationType;
	}

	public int getId() {
		return id;
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

	public MapCoordinate getLocation() {
		return location;
	}

	public LocationType getLocationType() {
		return locationType;
	}

	public ArrayList<ParticipantDTO> getParticipants() {
		return participants;
	}

	public HashSet<MapCoordinate> getPredefinedLocations() {
		return predefinedLocations;
	}

	public void setId(int id) {
		this.id = id;
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

	public void setLocation(MapCoordinate location) {
		this.location = location;
	}

	public void setLocationType(LocationType locationType) {
		this.locationType = locationType;
	}

	public void setParticipants(ArrayList<ParticipantDTO> participants) {
		this.participants = participants;
	}

	public boolean addParticipant(ParticipantDTO participant) {
		return participants.add(participant);
	}

	public void setPredefinedLocations(
			HashSet<MapCoordinate> predefinedLocations) {
		this.predefinedLocations = predefinedLocations;
	}

	public void addPredefinedLocation(MapCoordinate predefinedLocation) {
		this.predefinedLocations.add(predefinedLocation);
	}

	public void removePredefinedLocation(MapCoordinate predefinedLocation) {
		this.predefinedLocations.remove(predefinedLocation);
	}

}
