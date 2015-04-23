package ee.juhan.meetingorganizer.server.core.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
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
import ee.juhan.meetingorganizer.server.rest.domain.MeetingDTO;
import ee.juhan.meetingorganizer.server.rest.domain.ParticipantDTO;

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

	private double locationLatitude;

	private double locationLongitude;

	@Column(nullable = false)
	private LocationType locationType;

	@OneToMany(fetch = FetchType.LAZY)
	private Set<Participant> participants = new HashSet<>();

	protected Meeting() {
		super();
	}

	public Meeting(int leaderId, String title, String description,
			Date startDateTime, Date endDateTime, LocationType locationType) {
		super();
		this.leaderId = leaderId;
		this.title = title;
		this.description = description;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
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

	public double getLocationLatitude() {
		return locationLatitude;
	}

	public double getLocationLongitude() {
		return locationLongitude;
	}

	public LocationType getLocationType() {
		return locationType;
	}

	public Set<Participant> getParticipants() {
		return participants;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setLocationLatitude(double locationLatitude) {
		this.locationLatitude = locationLatitude;
	}

	public void setLocationLongitude(double locationLongitude) {
		this.locationLongitude = locationLongitude;
	}

	public boolean addParticipant(Participant participant) {
		return participants.add(participant);
	}

	public MeetingDTO toDTO(Meeting meeting, TimeZone clientTimeZone) {
		MeetingDTO meetingDTO = new MeetingDTO(meeting.getLeaderId(),
				meeting.getTitle(), meeting.getDescription(),
				DateParserUtil.fromClientTimeZone(meeting.getStartDateTime(),
						clientTimeZone), DateParserUtil.fromClientTimeZone(
						meeting.getEndDateTime(), clientTimeZone),
				meeting.getLocationLatitude(), meeting.getLocationLongitude(),
				meeting.getLocationType());
		for (Participant participant : meeting.getParticipants()) {
			ParticipantDTO participantDTO = new ParticipantDTO(
					participant.getAccountId(), participant.getName(),
					participant.getEmail(), participant.getPhoneNumber(),
					participant.getParticipationAnswer(),
					participant.getLocationLatitude(),
					participant.getLocationLongitude());
			meetingDTO.addParticipant(participantDTO);
		}
		return meetingDTO;
	}

}
