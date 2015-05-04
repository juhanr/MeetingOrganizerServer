package ee.juhan.meetingorganizer.server.core.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.persistence.Column;
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

	protected Meeting() {
		super();
	}

	public Meeting(int leaderId, String title, String description,
			Date startDateTime, Date endDateTime, MapCoordinate location,
			LocationType locationType) {
		super();
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

	public List<Participant> getParticipants() {
		return participants;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setLocation(MapCoordinate location) {
		this.location = location;
	}

	public boolean addParticipant(Participant participant) {
		return participants.add(participant);
	}

	public MeetingDTO toDTO(TimeZone clientTimeZone) {
		MeetingDTO meetingDTO = new MeetingDTO(id, leaderId, title,
				description, DateParserUtil.fromClientTimeZone(startDateTime,
						clientTimeZone), DateParserUtil.fromClientTimeZone(
						endDateTime, clientTimeZone), location, locationType);
		for (Participant participant : participants) {
			meetingDTO.addParticipant(participant.toDTO());
		}
		return meetingDTO;
	}

}
