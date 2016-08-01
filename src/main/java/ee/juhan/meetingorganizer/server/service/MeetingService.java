package ee.juhan.meetingorganizer.server.service;

import java.util.List;

import ee.juhan.meetingorganizer.server.rest.domain.MeetingDTO;
import ee.juhan.meetingorganizer.server.rest.domain.ParticipantDTO;

public interface MeetingService {

	MeetingDTO newMeeting(MeetingDTO meetingDTO, String sid);

	List<MeetingDTO> getActiveMeetings(int accountId, String sid);

	List<MeetingDTO> getPastMeetings(int accountId, String sid);

	List<MeetingDTO> getInvitations(int accountId, String sid);

	MeetingDTO updateParticipationAnswer(ParticipantDTO participantDTO, int meetingId, String sid);

	MeetingDTO updateParticipantLocation(ParticipantDTO participantDTO, int meetingId, String sid);

	MeetingDTO generateRecommendedLocations(int meetingId, String sid);

}
