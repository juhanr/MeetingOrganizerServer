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
import ee.juhan.meetingorganizer.server.rest.domain.LocationType;
import ee.juhan.meetingorganizer.server.rest.domain.MeetingDTO;
import ee.juhan.meetingorganizer.server.rest.domain.MeetingStatus;
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
	public final MeetingDTO newMeeting(MeetingDTO meetingDTO, String sid) {
		if (!isValidSID(meetingDTO.getLeaderId(), sid)) { return null; }
		Meeting meeting = createMeeting(meetingDTO);
		meetingRepository.save(meeting);
		addParticipants(meeting, meetingDTO);
		checkLocationType(meeting);
		return meeting.toDTO(participantRepository);
	}

	@Override
	public final List<MeetingDTO> getActiveMeetings(int accountId, String sid) {
		if (!isValidSID(accountId, sid)) { return null; }
		return meetingsListToDTO(participantRepository
				.findActiveMeetings(accountId, ParticipationAnswer.PARTICIPATING));
	}

	@Override
	public final List<MeetingDTO> getPastMeetings(int accountId, String sid) {
		if (!isValidSID(accountId, sid)) { return null; }
		return meetingsListToDTO(participantRepository
				.findPastMeetings(accountId, ParticipationAnswer.PARTICIPATING));
	}

	@Override
	public final List<MeetingDTO> getInvitations(int accountId, String sid) {
		if (!isValidSID(accountId, sid)) { return null; }
		return meetingsListToDTO(
				participantRepository.findInvitations(accountId, ParticipationAnswer.NO_ANSWER));
	}

	@Override
	public final MeetingDTO updateParticipationAnswer(ParticipantDTO participantDTO, int meetingId,
			String sid) {
		if (!isValidSID(participantDTO.getAccountId(), sid)) { return null; }
		Participant participant = participantRepository.findById(participantDTO.getId());
		participant.setParticipationAnswer(participantDTO.getParticipationAnswer());
		participantRepository.save(participant);
		Meeting meeting = meetingRepository.findById(meetingId);
		return meeting.toDTO(participantRepository);
	}

	@Override
	public final MeetingDTO updateParticipantLocation(ParticipantDTO participantDTO, int meetingId,
			String sid) {
		if (!isValidSID(participantDTO.getAccountId(), sid)) { return null; }
		Participant participant = participantRepository.findById(participantDTO.getId());
		participant.updateLocation(participantDTO);
		participantRepository.save(participant);
		Meeting meeting = meetingRepository.findById(meetingId);
		return meeting.toDTO(participantRepository);
	}

	@Override
	public final MeetingDTO generateRecommendedLocations(int meetingId, String sid) {
		Meeting meeting = meetingRepository.findById(meetingId);
		if (!isValidSID(meeting.getLeaderId(), sid) ||
				meeting.getStatus() != MeetingStatus.WAITING_PARTICIPANT_ANSWERS) {
			return null;
		}
		meeting.setStatus(MeetingStatus.WAITING_LOCATION_CHOICE);
		if (meeting.getLocationType() == LocationType.GENERATED_FROM_PREFERRED_LOCATIONS) {
			// TODO
		} else if (meeting.getLocationType() == LocationType.GENERATED_FROM_PARAMETERS) {
			// TODO
		}
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
				meetingDTO.getLocationName(), meetingDTO.getUserPreferredLocations(),
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
								participantDTO.getSendGPSLocationAnswer(),
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

	private void checkLocationType(Meeting meeting) {
		if (meeting.getLocationType() == LocationType.GENERATED_FROM_PARAMETERS ||
				meeting.getLocationType() == LocationType.GENERATED_FROM_PREFERRED_LOCATIONS) {
			meeting.setStatus(MeetingStatus.WAITING_LOCATION_CHOICE);
			meetingRepository.save(meeting);
		}
	}

}