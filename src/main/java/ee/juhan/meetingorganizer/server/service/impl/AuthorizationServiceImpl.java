package ee.juhan.meetingorganizer.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import ee.juhan.meetingorganizer.server.core.domain.Account;
import ee.juhan.meetingorganizer.server.core.domain.Meeting;
import ee.juhan.meetingorganizer.server.core.domain.Participant;
import ee.juhan.meetingorganizer.server.core.repository.AccountRepository;
import ee.juhan.meetingorganizer.server.core.repository.MeetingRepository;
import ee.juhan.meetingorganizer.server.core.repository.ParticipantRepository;
import ee.juhan.meetingorganizer.server.service.AuthorizationService;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private ParticipantRepository participantRepository;

	@Autowired
	private MeetingRepository meetingRepository;

	@Override
	public void authorizeAccount(int accountId, String sid) {
		Account account = accountRepository.findById(accountId);
		if (account == null || !account.getSid().equals(sid)) {
			throw new HttpServerErrorException(HttpStatus.UNAUTHORIZED);
		}
	}

	@Override
	public void authorizeParticipant(int participantId, String sid) {
		Participant participant = participantRepository.findById(participantId);
		Account account = accountRepository.findById(participant.getAccount().getId());
		if (account == null || !account.getSid().equals(sid)) {
			throw new HttpServerErrorException(HttpStatus.UNAUTHORIZED);
		}
	}

	@Override
	public void authorizeMeetingLeader(int meetingId, String sid) {
		Meeting meeting = meetingRepository.findById(meetingId);
		Account account = accountRepository.findById(meeting.getLeaderId());
		if (account == null || !account.getSid().equals(sid)) {
			throw new HttpServerErrorException(HttpStatus.UNAUTHORIZED);
		}
	}

}
