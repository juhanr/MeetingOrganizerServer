package ee.juhan.meetingorganizer.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import ee.juhan.meetingorganizer.server.core.domain.Participant;
import ee.juhan.meetingorganizer.server.core.repository.AccountRepository;
import ee.juhan.meetingorganizer.server.core.repository.MeetingRepository;
import ee.juhan.meetingorganizer.server.core.repository.ParticipantRepository;
import ee.juhan.meetingorganizer.server.rest.domain.ParticipantDto;
import ee.juhan.meetingorganizer.server.rest.domain.ParticipationAnswer;
import ee.juhan.meetingorganizer.server.rest.domain.SendGpsLocationAnswer;
import ee.juhan.meetingorganizer.server.service.ParticipantService;

@Service
public class ParticipantServiceImpl implements ParticipantService {

	@Autowired
	private MeetingRepository meetingRepository;

	@Autowired
	private ParticipantRepository participantRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public final void updateParticipationAnswer(int participantId,
			ParticipationAnswer participationAnswer) {
		Participant participant = participantRepository.findById(participantId);
		participant.setParticipationAnswer(participationAnswer);
		participantRepository.save(participant);
	}

	@Override
	public final void updateSendGpsLocationAnswer(int participantId,
			SendGpsLocationAnswer sendGpsLocationAnswer) {
		Participant participant = participantRepository.findById(participantId);
		participant.setSendGPSLocationAnswer(sendGpsLocationAnswer);
		participantRepository.save(participant);
	}

	@Override
	public final boolean updateLocationAll(ParticipantDto participantDto) {
		List<Participant> participants = participantRepository
				.findOngoingMeetingParticipantsByAccountId(participantDto.getAccountId(),
						SendGpsLocationAnswer.SEND);
		boolean mustSendMoreLocationUpdates = false;
		for (Participant participant : participants) {
			mustSendMoreLocationUpdates = true;
			participant.setMapLocation(participantDto.getMapLocation());
			participant.setLocationTimestamp(participantDto.getLocationTimestamp());
			participantRepository.save(participant);
		}
		return mustSendMoreLocationUpdates;
	}

}
