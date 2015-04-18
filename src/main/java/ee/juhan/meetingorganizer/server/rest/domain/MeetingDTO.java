package ee.juhan.meetingorganizer.server.rest.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class MeetingDTO {

	private int inviterUserId;
	private String title;
	private Date startTime;
	private Date endTime;
	private double locationLatitude;
	private double locationLongitude;
	private Set<ParticipantDTO> participants = new HashSet<>();
	private String message;

	public MeetingDTO() {
	}

	public int getInviterUserId() {
		return inviterUserId;
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

	public Set<ParticipantDTO> getParticipants() {
		return participants;
	}

	public String getMessage() {
		return message;
	}

	public boolean addParticipant(ParticipantDTO participant) {
		return participants.add(participant);
	}

}