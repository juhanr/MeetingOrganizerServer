package ee.juhan.meetingorganizer.server.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import ee.juhan.meetingorganizer.server.rest.domain.LocationChoice;
import ee.juhan.meetingorganizer.server.rest.domain.MeetingDto;
import ee.juhan.meetingorganizer.server.rest.domain.MeetingStatus;
import ee.juhan.meetingorganizer.server.rest.domain.ParticipantDto;
import ee.juhan.meetingorganizer.server.rest.domain.ParticipationAnswer;
import ee.juhan.meetingorganizer.server.service.MeetingService;

@Service
public class MeetingServiceImpl implements MeetingService {

	private static final Logger LOG = LoggerFactory.getLogger(MeetingServiceImpl.class);

	@Autowired
	private MeetingRepository meetingRepository;

	@Autowired
	private ParticipantRepository participantRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public final MeetingDto newMeeting(MeetingDto meetingDto) {
		Meeting meeting = createMeeting(meetingDto);
		meetingRepository.save(meeting);
		addParticipants(meeting, meetingDto);
		if (meeting.getLocationChoice() == LocationChoice.RECOMMENDED_BY_PLACE_TYPE ||
				meeting.getLocationChoice() ==
						LocationChoice.RECOMMENDED_FROM_PREFERRED_LOCATIONS) {
			meeting.setStatus(MeetingStatus.WAITING_LOCATION_CHOICE);
			meetingRepository.save(meeting);
		}
		return meeting.toDto(participantRepository);
	}

	@Override
	public final List<MeetingDto> getActiveMeetings(int accountId) {
		return meetingsListToDTO(participantRepository
				.findActiveMeetings(accountId, ParticipationAnswer.PARTICIPATING));
	}

	@Override
	public final List<MeetingDto> getPastMeetings(int accountId) {
		return meetingsListToDTO(participantRepository
				.findPastMeetings(accountId, ParticipationAnswer.PARTICIPATING));
	}

	@Override
	public final List<MeetingDto> getInvitations(int accountId) {
		return meetingsListToDTO(
				participantRepository.findInvitations(accountId, ParticipationAnswer.NO_ANSWER));
	}

	@Override
	public final MeetingDto getMeeting(int meetingId) {
		Meeting meeting = meetingRepository.findById(meetingId);
		return meeting.toDto(participantRepository);
	}

	@Override
	public final void updateMeeting(MeetingDto meetingDto) {
		Meeting meeting = meetingRepository.findById(meetingDto.getId());
		meeting.setTitle(meetingDto.getTitle());
		meeting.setDescription(meetingDto.getDescription());
		meeting.setMapLocation(meetingDto.getMapLocation());
		meeting.setLocationChoice(meetingDto.getLocationChoice());
		meeting.setStatus(meetingDto.getStatus());
		meetingRepository.save(meeting);
	}

	private List<MeetingDto> meetingsListToDTO(List<Meeting> meetings) {
		List<MeetingDto> meetingsDTO = new ArrayList<>();
		for (Meeting meeting : meetings) {
			meetingsDTO.add(meeting.toDto(participantRepository));
		}
		return meetingsDTO;
	}

	private Meeting createMeeting(MeetingDto meetingDto) {
		return new Meeting(meetingDto.getLeaderId(), meetingDto.getTitle(),
				meetingDto.getDescription(), meetingDto.getStartDateTime(),
				meetingDto.getEndDateTime(), meetingDto.getMapLocation(),
				meetingDto.getLocationChoice(), meetingDto.getUserPreferredLocations(),
				meetingDto.getStatus());
	}

	private Meeting addParticipants(Meeting meeting, MeetingDto meetingDto) {
		Participant participant;
		for (ParticipantDto participantDto : meetingDto.getParticipants()) {
			int participantAccountId = participantDto.getAccountId();
			if (participantAccountId != 0) {
				Account account = accountRepository.findById(participantAccountId);
				if (account == null) {
					LOG.debug("Account null: " + participantAccountId);
				} else {
					LOG.debug("Account not null: " + account.toString());
				}
				participant =
						new Participant(account, meeting, account.getName(), account.getEmail(),
								account.getPhoneNumber(), participantDto.getParticipationAnswer(),
								participantDto.getSendGpsLocationAnswer(),
								participantDto.getMapLocation(),
								participantDto.getLocationTimestamp());
			} else {
				participant = new Participant(meeting, participantDto.getName(),
						participantDto.getEmail(), participantDto.getPhoneNumber());
			}
			participantRepository.save(participant);
		}
		return meeting;
	}

}