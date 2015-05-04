package ee.juhan.meetingorganizer.server.service;

import java.util.List;

import ee.juhan.meetingorganizer.server.rest.domain.MeetingDTO;
import ee.juhan.meetingorganizer.server.rest.domain.ParticipantDTO;

public interface MeetingService {

	public MeetingDTO newMeetingRequest(MeetingDTO meetingDTO,
			String clientTimeZoneId, String sid);

	public List<MeetingDTO> getOngoingMeetingsRequest(int accountId,
			String clientTimeZoneId, String sid);

	public List<MeetingDTO> getFutureMeetingsRequest(int accountId,
			String clientTimeZoneId, String sid);

	public List<MeetingDTO> getPastMeetingsRequest(int accountId,
			String clientTimeZoneId, String sid);

	public List<MeetingDTO> getInvitationsRequest(int accountId,
			String clientTimeZoneId, String sid);

	public MeetingDTO updateParticipantRequest(ParticipantDTO participantDTO,
			int meetingId, String clientTimeZoneId, String sid);

}
