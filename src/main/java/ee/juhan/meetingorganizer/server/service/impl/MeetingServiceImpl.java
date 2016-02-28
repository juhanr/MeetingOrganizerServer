package ee.juhan.meetingorganizer.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ee.juhan.meetingorganizer.server.core.domain.Account;
import ee.juhan.meetingorganizer.server.core.domain.Meeting;
import ee.juhan.meetingorganizer.server.core.domain.Participant;
import ee.juhan.meetingorganizer.server.core.repository.AccountRepository;
import ee.juhan.meetingorganizer.server.core.repository.MeetingRepository;
import ee.juhan.meetingorganizer.server.core.repository.ParticipantRepository;
import ee.juhan.meetingorganizer.server.core.util.LocationGeneratorUtil;
import ee.juhan.meetingorganizer.server.rest.domain.MeetingDTO;
import ee.juhan.meetingorganizer.server.rest.domain.ParticipantDTO;
import ee.juhan.meetingorganizer.server.rest.domain.ParticipationAnswer;
import ee.juhan.meetingorganizer.server.service.MeetingService;

@Service
public class MeetingServiceImpl implements MeetingService {

	@Autowired
	private MeetingRepository meetingRepository;

	@Autowired
	private ParticipantRepository participantRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public final MeetingDTO newMeetingRequest(MeetingDTO meetingDTO, String sid) {
		if (!isValidSID(meetingDTO.getLeaderId(), sid)) { return null; }
		Meeting meeting = createMeeting(meetingDTO);
		meeting = addParticipants(meeting, meetingDTO);
		checkLocation(meeting);
		meetingRepository.save(meeting);
		addMeetingToParticipantAccounts(meeting);
		return meeting.toDTO();
	}

	@Override
	public final List<MeetingDTO> getCurrentMeetingsRequest(int accountId, String sid) {
		if (!isValidSID(accountId, sid)) { return null; }
		List<Meeting> currentMeetings =
				accountRepository.findCurrentMeetings(accountId, ParticipationAnswer.PARTICIPATING);
		List<MeetingDTO> currentMeetingsDTO = new ArrayList<>();
		for (Meeting meeting : currentMeetings) {
			currentMeetingsDTO.add(meeting.toDTO());
		}
		return currentMeetingsDTO;
	}

	@Override
	public final List<MeetingDTO> getFutureMeetingsRequest(int accountId, String sid) {
		if (!isValidSID(accountId, sid)) { return null; }
		List<Meeting> currentMeetings =
				accountRepository.findFutureMeetings(accountId, ParticipationAnswer.PARTICIPATING);
		List<MeetingDTO> currentMeetingsDTO = new ArrayList<>();
		for (Meeting meeting : currentMeetings) {
			currentMeetingsDTO.add(meeting.toDTO());
		}
		return currentMeetingsDTO;
	}

	@Override
	public final List<MeetingDTO> getPastMeetingsRequest(int accountId, String sid) {
		if (!isValidSID(accountId, sid)) { return null; }
		List<Meeting> currentMeetings =
				accountRepository.findPastMeetings(accountId, ParticipationAnswer.PARTICIPATING);
		List<MeetingDTO> currentMeetingsDTO = new ArrayList<>();
		for (Meeting meeting : currentMeetings) {
			currentMeetingsDTO.add(meeting.toDTO());
		}
		return currentMeetingsDTO;
	}

	@Override
	public final List<MeetingDTO> getInvitationsRequest(int accountId, String sid) {
		if (!isValidSID(accountId, sid)) { return null; }
		List<Meeting> currentMeetings =
				accountRepository.findCurrentMeetings(accountId, ParticipationAnswer.NOT_ANSWERED);
		List<MeetingDTO> currentMeetingsDTO = new ArrayList<>();
		for (Meeting meeting : currentMeetings) {
			currentMeetingsDTO.add(meeting.toDTO());
		}
		return currentMeetingsDTO;
	}

	@Override
	public final MeetingDTO updateParticipantRequest(ParticipantDTO participantDTO, int meetingId,
			String sid) {
		if (!isValidSID(participantDTO.getAccountId(), sid)) { return null; }
		Participant participant = participantRepository.findById(participantDTO.getId());
		participant.updateInfo(participantDTO);
		participantRepository.save(participant);
		Meeting meeting = meetingRepository.findById(meetingId);
		checkLocation(meeting);
		return meeting.toDTO();
	}

	private Meeting createMeeting(MeetingDTO meetingDTO) {
		return new Meeting(meetingDTO.getLeaderId(), meetingDTO.getTitle(),
				meetingDTO.getDescription(), meetingDTO.getStartDateTime(),
				meetingDTO.getEndDateTime(), meetingDTO.getLocation(), meetingDTO.getLocationType(),
				meetingDTO.getPredefinedLocations());
	}

	private Meeting addParticipants(Meeting meeting, MeetingDTO meetingDTO) {
		Participant participant;
		for (ParticipantDTO participantDTO : meetingDTO.getParticipants()) {
			int participantAccountId = participantDTO.getAccountId();
			if (participantAccountId != 0) {
				Account account = accountRepository.findById(participantAccountId);
				participant =
						new Participant(participantAccountId, account.getName(), account.getEmail(),
								account.getPhoneNumber(), participantDTO.getParticipationAnswer(),
								participantDTO.getLocation());
			} else {
				participant = new Participant(participantDTO.getName(), participantDTO.getEmail(),
						participantDTO.getPhoneNumber());
			}
			participantRepository.save(participant);
			meeting.addParticipant(participant);
		}
		return meeting;
	}

	private void addMeetingToParticipantAccounts(Meeting meeting) {
		for (Participant participant : meeting.getParticipants()) {
			if (participant.getAccountId() != 0) {
				Account account = accountRepository.findById(participant.getAccountId());
				account.addMeeting(meeting);
				accountRepository.save(account);
			}
		}
	}

	private boolean isValidSID(int accountId, String sid) {
		Account account = accountRepository.findById(accountId);
		return account != null && account.getSid().equals(sid);
	}

	private Date addMinute(Date date) {
		return new Date(date.getTime() + TimeUnit.MINUTES.toMillis(1));
	}

	private void checkLocation(Meeting meeting) {
		if (meeting.getLocation() == null) {
			for (Participant participant : meeting.getParticipants()) {
				if (participant.getParticipationAnswer() == ParticipationAnswer.NOT_ANSWERED) {
					return;
				}
			}
			meeting.setLocation(LocationGeneratorUtil.findOptimalLocation(meeting));
			meetingRepository.save(meeting);
		}
	}

}