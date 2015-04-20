package ee.juhan.meetingorganizer.server.core.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ee.juhan.meetingorganizer.server.rest.domain.LocationType;

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

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date startTime;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date endTime;

	private double locationLatitude;

	private double locationLongitude;

	@Column(nullable = false)
	private LocationType locationType;

	@OneToMany(fetch = FetchType.LAZY)
	private Set<Participant> participants = new HashSet<>();

	private String message;

	public Meeting(int leaderId, String title, Date startTime, Date endTime,
			String message, LocationType locationType) {
		this.leaderId = leaderId;
		this.title = title;
		this.startTime = startTime;
		this.endTime = endTime;
		this.message = message;
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

	public Date getStartTime() {
		return startTime;
	}

	public Date getEndTime() {
		return endTime;
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

	public String getMessage() {
		return message;
	}

	public void setLocationLatitude(double locationLatitude) {
		this.locationLatitude = locationLatitude;
	}

	public void setLocationLongitude(double locationLongitude) {
		this.locationLongitude = locationLongitude;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean addParticipant(Participant participant) {
		return participants.add(participant);
	}

}
