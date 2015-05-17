package ee.juhan.meetingorganizer.server.core.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ee.juhan.meetingorganizer.server.core.util.DateParserUtil;
import ee.juhan.meetingorganizer.server.rest.domain.LocationType;
import ee.juhan.meetingorganizer.server.rest.domain.MapCoordinate;
import ee.juhan.meetingorganizer.server.rest.domain.MeetingDTO;

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

	@OneToMany(fetch = FetchType.LAZY)
	private List<Participant> participants = new ArrayList<>();

	@ElementCollection
	private Set<MapCoordinate> predefinedLocations = new HashSet<>();

	protected Meeting() {}

	public Meeting(int leaderId, String title, String description, Date startDateTime,
			Date endDateTime, MapCoordinate location, LocationType locationType,
			Set<MapCoordinate> predefinedLocations) {
		this.leaderId = leaderId;
		this.title = title;
		this.description = description;
		this.startDateTime = (Date) startDateTime.clone();
		this.endDateTime = (Date) endDateTime.clone();
		this.location = location;
		this.locationType = locationType;
		this.predefinedLocations = predefinedLocations;
	}

	public final int getId() {
		return id;
	}

	public final int getLeaderId() {
		return leaderId;
	}

	public final String getTitle() {
		return title;
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

	public final Date getEndDateTime() {
		return (Date) endDateTime.clone();
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

	public final List<Participant> getParticipants() {
		return participants;
	}

	public final Set<MapCoordinate> getPredefinedLocations() {
		return predefinedLocations;
	}

	public final void setPredefinedLocations(Set<MapCoordinate> predefinedLocations) {
		this.predefinedLocations = predefinedLocations;
	}

	public final boolean addParticipant(Participant participant) {
		return participants.add(participant);
	}

	public final void addPredefinedLocation(MapCoordinate predefinedLocation) {
		this.predefinedLocations.add(predefinedLocation);
	}

	public final void removePredefinedLocation(MapCoordinate predefinedLocation) {
		this.predefinedLocations.remove(predefinedLocation);
	}

	public final MeetingDTO toDTO(TimeZone clientTimeZone) {
		MeetingDTO meetingDTO = new MeetingDTO(id, leaderId, title, description,
				DateParserUtil.fromClientTimeZone(startDateTime, clientTimeZone),
				DateParserUtil.fromClientTimeZone(endDateTime, clientTimeZone), location,
				locationType);
		for (Participant participant : participants) {
			meetingDTO.addParticipant(participant.toDTO());
		}
		return meetingDTO;
	}

}
