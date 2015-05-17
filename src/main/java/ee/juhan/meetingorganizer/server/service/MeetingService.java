package ee.juhan.meetingorganizer.server.service;

import java.util.List;

import ee.juhan.meetingorganizer.server.rest.domain.MeetingDTO;
import ee.juhan.meetingorganizer.server.rest.domain.ParticipantDTO;

public interface MeetingService {

	MeetingDTO newMeetingRequest(MeetingDTO meetingDTO, String clientTimeZoneId, String sid);

	List<MeetingDTO> getOngoingMeetingsRequest(int accountId, String clientTimeZoneId, String sid);

	List<MeetingDTO> getFutureMeetingsRequest(int accountId, String clientTimeZoneId, String sid);

	List<MeetingDTO> getPastMeetingsRequest(int accountId, String clientTimeZoneId, String sid);

	List<MeetingDTO> getInvitationsRequest(int accountId, String clientTimeZoneId, String sid);

	MeetingDTO updateParticipantRequest(ParticipantDTO participantDTO, int meetingId,
			String clientTimeZoneId, String sid);

}
