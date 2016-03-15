package ee.juhan.meetingorganizer.server.service;

import java.util.List;

import ee.juhan.meetingorganizer.server.rest.domain.MeetingDTO;
import ee.juhan.meetingorganizer.server.rest.domain.ParticipantDTO;

public interface MeetingService {

	MeetingDTO newMeetingRequest(MeetingDTO meetingDTO, String sid);

	List<MeetingDTO> getActiveMeetingsRequest(int accountId, String sid);

	List<MeetingDTO> getPastMeetingsRequest(int accountId, String sid);

	List<MeetingDTO> getInvitationsRequest(int accountId, String sid);

	MeetingDTO updateParticipantRequest(ParticipantDTO participantDTO, int meetingId, String sid);

}
