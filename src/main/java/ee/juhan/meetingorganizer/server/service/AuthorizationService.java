package ee.juhan.meetingorganizer.server.service;

public interface AuthorizationService {

	void authorizeAccount(int accountId, String sid);

	void authorizeParticipant(int participantId, String sid);

	void authorizeMeetingLeader(int meetingId, String sid);
}
