package ee.juhan.meetingorganizer.server.service;

import java.util.List;

import ee.juhan.meetingorganizer.server.rest.domain.MeetingDto;

public interface MeetingService {

	MeetingDto newMeeting(MeetingDto meetingDto);

	List<MeetingDto> getActiveMeetings(int accountId);

	List<MeetingDto> getPastMeetings(int accountId);

	List<MeetingDto> getInvitations(int accountId);

	MeetingDto generateRecommendedLocations(int meetingId);

}
