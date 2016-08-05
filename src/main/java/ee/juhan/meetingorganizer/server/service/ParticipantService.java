package ee.juhan.meetingorganizer.server.service;

import ee.juhan.meetingorganizer.server.rest.domain.ParticipantDto;
import ee.juhan.meetingorganizer.server.rest.domain.ParticipationAnswer;
import ee.juhan.meetingorganizer.server.rest.domain.SendGpsLocationAnswer;

public interface ParticipantService {

	void updateParticipationAnswer(int participantId, ParticipationAnswer participationAnswer);

	void updateSendGpsLocationAnswer(int participantId,
			SendGpsLocationAnswer sendGpsLocationAnswer);

	boolean updateLocationAll(ParticipantDto participantDto);

}
