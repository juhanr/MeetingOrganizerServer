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
	private MapCoordinate location;
	private LocationType locationType;
	private String locationName;
	private List<ParticipantDto> participants = new ArrayList<>();
	private List<MapCoordinate> userPreferredLocations = new ArrayList<>();
	private MapCoordinate recommendedLocation;
	private MeetingStatus status;

	public MeetingDto() {}

	public MeetingDto(int id, int leaderId, String title, String description, Date startDateTime,
			Date endDateTime, MapCoordinate location, LocationType locationType,
			String locationName, List<MapCoordinate> userPreferredLocations,
			MapCoordinate recommendedLocation, MeetingStatus status) {
		this.id = id;
		this.leaderId = leaderId;
		this.title = title;
		this.description = description;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.location = location;
		this.locationType = locationType;
		this.locationName = locationName;
		this.userPreferredLocations = userPreferredLocations;
		this.recommendedLocation = recommendedLocation;
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

	public final MapCoordinate getLocation() {
		return location;
	}

	public final void setLocation(MapCoordinate location) {
		this.location = location;
	}

	public final LocationType getLocationType() {
		return locationType;
	}

	public final void setLocationType(LocationType locationType) {
		this.locationType = locationType;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
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

	public final List<MapCoordinate> getUserPreferredLocations() {
		return userPreferredLocations;
	}

	public final void setUserPreferredLocations(List<MapCoordinate> userPreferredLocations) {
		this.userPreferredLocations = userPreferredLocations;
	}

	public final void addUserPreferredLocation(MapCoordinate userPreferredLocation) {
		this.userPreferredLocations.add(userPreferredLocation);
	}

	public final void removeUserPreferredLocation(MapCoordinate userPreferredLocation) {
		this.userPreferredLocations.remove(userPreferredLocation);
	}

	public MapCoordinate getRecommendedLocation() {
		return recommendedLocation;
	}

	public void setRecommendedLocation(MapCoordinate recommendedLocation) {
		this.recommendedLocation = recommendedLocation;
	}

	public MeetingStatus getStatus() {
		return status;
	}

	public void setStatus(MeetingStatus status) {
		this.status = status;
	}

}
