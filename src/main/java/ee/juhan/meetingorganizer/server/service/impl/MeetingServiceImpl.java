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
import ee.juhan.meetingorganizer.server.rest.domain.MeetingDto;
import ee.juhan.meetingorganizer.server.rest.domain.MeetingStatus;
import ee.juhan.meetingorganizer.server.rest.domain.ParticipantDto;
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
	public final MeetingDto newMeeting(MeetingDto meetingDto, String sid) {
		if (!isValidSID(meetingDto.getLeaderId(), sid)) { return null; }
		Meeting meeting = createMeeting(meetingDto);
		meetingRepository.save(meeting);
		addParticipants(meeting, meetingDto);
		checkLocationType(meeting);
		return meeting.toDTO(participantRepository);
	}

	@Override
	public final List<MeetingDto> getActiveMeetings(int accountId, String sid) {
		if (!isValidSID(accountId, sid)) { return null; }
		return meetingsListToDTO(participantRepository
				.findActiveMeetings(accountId, ParticipationAnswer.PARTICIPATING));
	}

	@Override
	public final List<MeetingDto> getPastMeetings(int accountId, String sid) {
		if (!isValidSID(accountId, sid)) { return null; }
		return meetingsListToDTO(participantRepository
				.findPastMeetings(accountId, ParticipationAnswer.PARTICIPATING));
	}

	@Override
	public final List<MeetingDto> getInvitations(int accountId, String sid) {
		if (!isValidSID(accountId, sid)) { return null; }
		return meetingsListToDTO(
				participantRepository.findInvitations(accountId, ParticipationAnswer.NO_ANSWER));
	}

	@Override
	public final MeetingDto updateParticipationAnswer(ParticipantDto participantDto, int meetingId,
			String sid) {
		if (!isValidSID(participantDto.getAccountId(), sid)) { return null; }
		Participant participant = participantRepository.findById(participantDto.getId());
		participant.setParticipationAnswer(participantDto.getParticipationAnswer());
		participantRepository.save(participant);
		Meeting meeting = meetingRepository.findById(meetingId);
		return meeting.toDTO(participantRepository);
	}

	@Override
	public final MeetingDto updateParticipantLocation(ParticipantDto participantDto, int meetingId,
			String sid) {
		if (!isValidSID(participantDto.getAccountId(), sid)) { return null; }
		Participant participant = participantRepository.findById(participantDto.getId());
		participant.updateLocation(participantDto);
		participantRepository.save(participant);
		Meeting meeting = meetingRepository.findById(meetingId);
		return meeting.toDTO(participantRepository);
	}

	@Override
	public final MeetingDto generateRecommendedLocations(int meetingId, String sid) {
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

	private List<MeetingDto> meetingsListToDTO(List<Meeting> meetings) {
		List<MeetingDto> meetingsDTO = new ArrayList<>();
		for (Meeting meeting : meetings) {
			meetingsDTO.add(meeting.toDTO(participantRepository));
		}
		return meetingsDTO;
	}

	private Meeting createMeeting(MeetingDto meetingDto) {
		return new Meeting(meetingDto.getLeaderId(), meetingDto.getTitle(),
				meetingDto.getDescription(), meetingDto.getStartDateTime(),
				meetingDto.getEndDateTime(), meetingDto.getLocation(), meetingDto.getLocationType(),
				meetingDto.getLocationName(), meetingDto.getUserPreferredLocations(),
				meetingDto.getStatus());
	}

	private Meeting addParticipants(Meeting meeting, MeetingDto meetingDto) {
		Participant participant;
		for (ParticipantDto participantDto : meetingDto.getParticipants()) {
			int participantAccountId = participantDto.getAccountId();
			if (participantAccountId != 0) {
				Account account = accountRepository.findById(participantAccountId);
				participant =
						new Participant(account, meeting, account.getName(), account.getEmail(),
								account.getPhoneNumber(), participantDto.getParticipationAnswer(),
								participantDto.getSendGpsLocationAnswer(),
								participantDto.getLocation(),
								participantDto.getLocationTimestamp());
			} else {
				participant = new Participant(meeting, participantDto.getName(),
						participantDto.getEmail(), participantDto.getPhoneNumber());
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