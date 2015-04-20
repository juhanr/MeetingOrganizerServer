package ee.juhan.meetingorganizer.server.rest.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class MeetingDTO {

	private int leaderId;
	private String title;
	private String message;
	private Date startTime;
	private Date endTime;
	private double locationLatitude;
	private double locationLongitude;
	private LocationType locationType;
	private Set<ParticipantDTO> participants = new HashSet<>();

	public MeetingDTO(int leaderId, String title, String message,
			Date startTime, Date endTime, double locationLatitude,
			double locationLongitude, LocationType locationType) {
		this.leaderId = leaderId;
		this.title = title;
		this.message = message;
		this.startTime = startTime;
		this.endTime = endTime;
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

	public String getMessage() {
		return message;
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

	public Set<ParticipantDTO> getParticipants() {
		return participants;
	}

	public void setLeaderId(int leaderId) {
		this.leaderId = leaderId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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
