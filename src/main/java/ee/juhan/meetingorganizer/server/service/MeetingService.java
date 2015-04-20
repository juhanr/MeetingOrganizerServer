package ee.juhan.meetingorganizer.server.service;

import java.util.List;

import ee.juhan.meetingorganizer.server.rest.domain.MeetingDTO;
import ee.juhan.meetingorganizer.server.rest.domain.ServerResponse;

public interface MeetingService {

	public ServerResponse newMeetingRequest(MeetingDTO meetingDTO, String sid);

	public List<MeetingDTO> getOngoingMeetingsRequest(int accountId,
			String clientLocalTime, String sid);

	public List<MeetingDTO> getFutureMeetingsRequest(int accountId,
			String clientLocalTime, String sid);

	public List<MeetingDTO> getPastMeetingsRequest(int accountId,
			String clientLocalTime, String sid);

	public List<MeetingDTO> getInvitationsRequest(int accountId,
			String clientLocalTime, String sid);

}
