package ee.juhan.meetingorganizer.server.core.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ee.juhan.meetingorganizer.server.core.repository.ParticipantRepository;
import ee.juhan.meetingorganizer.server.rest.domain.LocationType;
import ee.juhan.meetingorganizer.server.rest.domain.MapCoordinate;
import ee.juhan.meetingorganizer.server.rest.domain.MeetingDto;
import ee.juhan.meetingorganizer.server.rest.domain.MeetingStatus;
import ee.juhan.meetingorganizer.server.rest.domain.ParticipantDto;

@Entity
public class Meeting implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private int leaderId;

	@Column(nullable = false)
	private String title;

	private String description;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDateTime;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDateTime;

	private MapCoordinate location;

	@Column(nullable = false)
	private LocationType locationType;

	private String locationName;

	@ElementCollection
	private List<MapCoordinate> userPreferredLocations = new ArrayList<>();

	private MapCoordinate recommendedLocation;

	@Column(nullable = false)
	private MeetingStatus status = MeetingStatus.ACTIVE;

	protected Meeting() {}

	public Meeting(int leaderId, String title, String description, Date startDateTime,
			Date endDateTime, MapCoordinate location, LocationType locationType,
			String locationName, List<MapCoordinate> userPreferredLocations, MeetingStatus status) {
		this.leaderId = leaderId;
		this.title = title;
		this.description = description;
		this.startDateTime = (Date) startDateTime.clone();
		this.endDateTime = (Date) endDateTime.clone();
		this.location = location;
		this.locationType = locationType;
		this.userPreferredLocations = userPreferredLocations;
		this.status = status;
	}

	public Meeting(int leaderId, String title, String description, Date startDateTime,
			Date endDateTime, MapCoordinate location, LocationType locationType,
			String locationName, MeetingStatus status) {
		this.leaderId = leaderId;
		this.title = title;
		this.description = description;
		this.startDateTime = (Date) startDateTime.clone();
		this.endDateTime = (Date) endDateTime.clone();
		this.location = location;
		this.locationType = locationType;
		this.status = status;
	}

	public final int getId() {
		return id;
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

	public final void setStartDateTime(Date startDateTime) {
		this.startDateTime = (Date) startDateTime.clone();
	}

	public final Date getEndDateTime() {
		return (Date) endDateTime.clone();
	}

	public final void setEndDateTime(Date endDateTime) {
		this.endDateTime = (Date) endDateTime.clone();
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

	public final MeetingDto toDto(ParticipantRepository participantRepository) {
		MeetingDto meetingDto =
				new MeetingDto(id, leaderId, title, description, startDateTime, endDateTime,
						location, locationType, locationName, userPreferredLocations,
						recommendedLocation, status);
		List<Participant> participants = participantRepository.findParticipantsByMeetingId(this.id);
		for (Participant participant : participants) {
			ParticipantDto participantDto = participant.toDTO();
			meetingDto.addParticipant(participantDto);
		}
		return meetingDto;
	}

}
