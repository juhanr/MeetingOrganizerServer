package ee.juhan.meetingorganizer.server.service;

import java.util.List;

import ee.juhan.meetingorganizer.server.rest.domain.MeetingDTO;
import ee.juhan.meetingorganizer.server.rest.domain.ServerResult;

public interface MeetingService {

	public ServerResult newMeetingRequest(MeetingDTO meetingDTO, String sid);

	public List<MeetingDTO> getOngoingMeetingsRequest(int accountId,
			String clientTimeZone, String sid);

	public List<MeetingDTO> getFutureMeetingsRequest(int accountId,
			String clientTimeZone, String sid);

	public List<MeetingDTO> getPastMeetingsRequest(int accountId,
			String clientTimeZone, String sid);

	public List<MeetingDTO> getInvitationsRequest(int accountId,
			String clientTimeZone, String sid);

}
