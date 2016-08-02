package ee.juhan.meetingorganizer.server.service;

import java.util.List;

import ee.juhan.meetingorganizer.server.rest.domain.MeetingDto;
import ee.juhan.meetingorganizer.server.rest.domain.ParticipantDto;

public interface MeetingService {

	MeetingDto newMeeting(MeetingDto meetingDto, String sid);

	List<MeetingDto> getActiveMeetings(int accountId, String sid);

	List<MeetingDto> getPastMeetings(int accountId, String sid);

	List<MeetingDto> getInvitations(int accountId, String sid);

	MeetingDto updateParticipationAnswer(ParticipantDto participantDto, int meetingId, String sid);

	MeetingDto updateParticipantLocation(ParticipantDto participantDto, int meetingId, String sid);

	MeetingDto generateRecommendedLocations(int meetingId, String sid);

}
