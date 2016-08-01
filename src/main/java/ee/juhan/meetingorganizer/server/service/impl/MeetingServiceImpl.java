package ee.juhan.meetingorganizer.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
		meetingRepository.save(meeting);
		addParticipants(meeting, meetingDTO);
		return meeting.toDTO(participantRepository);
	}

	@Override
	public final List<MeetingDTO> getActiveMeetingsRequest(int accountId, String sid) {
		if (!isValidSID(accountId, sid)) { return null; }
		return meetingsListToDTO(participantRepository
				.findActiveMeetings(accountId, ParticipationAnswer.PARTICIPATING));
	}

	@Override
	public final List<MeetingDTO> getPastMeetingsRequest(int accountId, String sid) {
		if (!isValidSID(accountId, sid)) { return null; }
		return meetingsListToDTO(participantRepository
				.findPastMeetings(accountId, ParticipationAnswer.PARTICIPATING));
	}

	@Override
	public final List<MeetingDTO> getInvitationsRequest(int accountId, String sid) {
		if (!isValidSID(accountId, sid)) { return null; }
		return meetingsListToDTO(
				participantRepository.findInvitations(accountId, ParticipationAnswer.NOT_ANSWERED));
	}

	@Override
	public final MeetingDTO updateParticipantRequest(ParticipantDTO participantDTO, int meetingId,
			String sid) {
		if (!isValidSID(participantDTO.getAccountId(), sid)) { return null; }
		Participant participant = participantRepository.findById(participantDTO.getId());
		participant.updateInfo(participantDTO);
		participantRepository.save(participant);
		Meeting meeting = meetingRepository.findById(meetingId);
		return meeting.toDTO(participantRepository);
	}

	private List<MeetingDTO> meetingsListToDTO(List<Meeting> meetings) {
		List<MeetingDTO> meetingsDTO = new ArrayList<>();
		for (Meeting meeting : meetings) {
			meetingsDTO.add(meeting.toDTO(participantRepository));
		}
		return meetingsDTO;
	}

	private Meeting createMeeting(MeetingDTO meetingDTO) {
		return new Meeting(meetingDTO.getLeaderId(), meetingDTO.getTitle(),
				meetingDTO.getDescription(), meetingDTO.getStartDateTime(),
				meetingDTO.getEndDateTime(), meetingDTO.getLocation(), meetingDTO.getLocationType(),
				meetingDTO.getLocationName(), meetingDTO.getPredefinedLocations(),
				meetingDTO.getStatus());
	}

	private Meeting addParticipants(Meeting meeting, MeetingDTO meetingDTO) {
		Participant participant;
		for (ParticipantDTO participantDTO : meetingDTO.getParticipants()) {
			int participantAccountId = participantDTO.getAccountId();
			if (participantAccountId != 0) {
				Account account = accountRepository.findById(participantAccountId);
				participant =
						new Participant(account, meeting, account.getName(), account.getEmail(),
								account.getPhoneNumber(), participantDTO.getParticipationAnswer(),
								participantDTO.getLocation(),
								participantDTO.getLocationTimestamp());
			} else {
				participant = new Participant(meeting, participantDTO.getName(),
						participantDTO.getEmail(), participantDTO.getPhoneNumber());
			}
			participantRepository.save(participant);
		}
		return meeting;
	}

	private boolean isValidSID(int accountId, String sid) {
		Account account = accountRepository.findById(accountId);
		return account != null && account.getSid().equals(sid);
	}

	private void checkLocation(Meeting meeting) {
		if (meeting.getLocation() == null) {
			List<Participant> participants =
					participantRepository.findParticipantsByMeetingId(meeting.getId());
			for (Participant participant : participants) {
				if (participant.getParticipationAnswer() == ParticipationAnswer.NOT_ANSWERED) {
					return;
				}
			}
			meeting.setLocation(LocationGeneratorUtil.findOptimalLocation(meeting, participants));
			meetingRepository.save(meeting);
		}
	}

}