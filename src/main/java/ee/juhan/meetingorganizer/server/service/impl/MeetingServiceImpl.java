package ee.juhan.meetingorganizer.server.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.juhan.meetingorganizer.server.core.domain.Account;
import ee.juhan.meetingorganizer.server.core.domain.Meeting;
import ee.juhan.meetingorganizer.server.core.domain.Participant;
import ee.juhan.meetingorganizer.server.core.repository.AccountRepository;
import ee.juhan.meetingorganizer.server.core.repository.MeetingRepository;
import ee.juhan.meetingorganizer.server.core.repository.ParticipantRepository;
import ee.juhan.meetingorganizer.server.core.util.DateParserUtil;
import ee.juhan.meetingorganizer.server.core.util.MeetingDTOComparator;
import ee.juhan.meetingorganizer.server.rest.domain.MeetingDTO;
import ee.juhan.meetingorganizer.server.rest.domain.ParticipantDTO;
import ee.juhan.meetingorganizer.server.rest.domain.ParticipationAnswer;
import ee.juhan.meetingorganizer.server.rest.domain.ServerResult;
import ee.juhan.meetingorganizer.server.service.MeetingService;

@Service
public class MeetingServiceImpl implements MeetingService {

	private TimeZone clientTimeZone = null;

	@Autowired
	private MeetingRepository meetingRepository;

	@Autowired
	private ParticipantRepository participantRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public ServerResult newMeetingRequest(MeetingDTO meetingDTO, String sid) {
		if (!isValidSID(meetingDTO.getLeaderId(), sid))
			return null;
		Meeting meeting = createMeeting(meetingDTO);
		meeting = addParticipants(meeting, meetingDTO);
		meetingRepository.save(meeting);
		addMeetingToParticipantAccounts(meeting);
		return ServerResult.SUCCESS;
	}

	private Meeting createMeeting(MeetingDTO meetingDTO) {

		Meeting meeting = new Meeting(meetingDTO.getLeaderId(),
				meetingDTO.getTitle(), meetingDTO.getDescription(),
				meetingDTO.getStartDateTime(), meetingDTO.getEndDateTime(),
				meetingDTO.getLocationType());
		if (meetingDTO.getLocationLatitude() != 0) {
			meeting.setLocationLatitude(meetingDTO.getLocationLatitude());
			meeting.setLocationLongitude(meetingDTO.getLocationLongitude());
		}
		return meeting;
	}

	private Meeting addParticipants(Meeting meeting, MeetingDTO meetingDTO) {
		Participant participant;
		for (ParticipantDTO participantDTO : meetingDTO.getParticipants()) {
			int participantAccountId = participantDTO.getAccountId();
			if (participantAccountId != 0) {
				Account account = accountRepository
						.findById(participantAccountId);
				participant = new Participant(participantAccountId,
						account.getName(), account.getEmail(),
						account.getPhoneNumber(),
						participantDTO.getParticipationAnswer(),
						participantDTO.getLocationLatitude(),
						participantDTO.getLocationLongitude());
			} else {
				participant = new Participant(participantDTO.getName(),
						participantDTO.getEmail(),
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
				Account account = accountRepository.findById(participant
						.getAccountId());
				account.addMeeting(meeting);
				accountRepository.save(account);
			}
		}
	}

	@Override
	public List<MeetingDTO> getOngoingMeetingsRequest(int accountId,
			String clientTimeZoneId, String sid) {
		if (!isValidSID(accountId, sid))
			return null;
		this.clientTimeZone = TimeZone.getTimeZone(clientTimeZoneId);
		Date clientLocalTime = DateParserUtil.toClientTimeZone(new Date(),
				clientTimeZone);
		List<MeetingDTO> responseList = new ArrayList<MeetingDTO>();
		List<Meeting> meetings = accountRepository.findMeetingsById(accountId);
		for (Meeting meeting : meetings) {
			if ((meeting.getStartDateTime().before(clientLocalTime) || meeting
					.getStartDateTime().equals(clientLocalTime))
					&& (meeting.getEndDateTime().after(clientLocalTime) || meeting
							.getEndDateTime().equals(clientLocalTime))) {
				for (Participant participant : meeting.getParticipants()) {
					if (participant.getAccountId() == accountId
							&& participant.getParticipationAnswer() == ParticipationAnswer.PARTICIPATING) {
						responseList
								.add(meeting.toDTO(meeting, clientTimeZone));
						break;
					}
				}
			}
		}
		Collections.sort(responseList, new MeetingDTOComparator());
		return responseList;
	}

	@Override
	public List<MeetingDTO> getFutureMeetingsRequest(int accountId,
			String clientTimeZoneId, String sid) {
		if (!isValidSID(accountId, sid))
			return null;
		this.clientTimeZone = TimeZone.getTimeZone(clientTimeZoneId);
		Date clientLocalTime = DateParserUtil.toClientTimeZone(new Date(),
				clientTimeZone);
		List<MeetingDTO> responseList = new ArrayList<MeetingDTO>();
		List<Meeting> meetings = accountRepository.findMeetingsById(accountId);
		for (Meeting meeting : meetings) {
			if (meeting.getStartDateTime().after(clientLocalTime)) {
				for (Participant participant : meeting.getParticipants()) {
					if (participant.getAccountId() == accountId
							&& participant.getParticipationAnswer() == ParticipationAnswer.PARTICIPATING) {
						responseList
								.add(meeting.toDTO(meeting, clientTimeZone));
						break;
					}
				}
			}
		}
		Collections.sort(responseList, new MeetingDTOComparator());
		return responseList;
	}

	@Override
	public List<MeetingDTO> getPastMeetingsRequest(int accountId,
			String clientTimeZoneId, String sid) {
		if (!isValidSID(accountId, sid))
			return null;
		this.clientTimeZone = TimeZone.getTimeZone(clientTimeZoneId);
		Date clientLocalTime = DateParserUtil.toClientTimeZone(new Date(),
				clientTimeZone);
		List<MeetingDTO> responseList = new ArrayList<MeetingDTO>();
		List<Meeting> meetings = accountRepository.findMeetingsById(accountId);
		for (Meeting meeting : meetings) {
			if (meeting.getEndDateTime().before(clientLocalTime)) {
				for (Participant participant : meeting.getParticipants()) {
					if (participant.getAccountId() == accountId
							&& participant.getParticipationAnswer() == ParticipationAnswer.PARTICIPATING) {
						responseList
								.add(meeting.toDTO(meeting, clientTimeZone));
						break;
					}
				}
			}
		}
		Collections.sort(responseList, new MeetingDTOComparator());
		return responseList;
	}

	@Override
	public List<MeetingDTO> getInvitationsRequest(int accountId,
			String clientTimeZoneId, String sid) {
		if (!isValidSID(accountId, sid))
			return null;
		clientTimeZone = TimeZone.getTimeZone(clientTimeZoneId);
		Date clientLocalTime = DateParserUtil.toClientTimeZone(new Date(),
				clientTimeZone);
		List<MeetingDTO> responseList = new ArrayList<MeetingDTO>();
		List<Meeting> meetings = accountRepository.findMeetingsById(accountId);
		for (Meeting meeting : meetings) {
			if (meeting.getEndDateTime().after(clientLocalTime)) {
				for (Participant participant : meeting.getParticipants()) {
					if (participant.getAccountId() == accountId
							&& participant.getParticipationAnswer() == ParticipationAnswer.NOT_ANSWERED) {
						responseList
								.add(meeting.toDTO(meeting, clientTimeZone));
						break;
					}
				}
			}
		}
		Collections.sort(responseList, new MeetingDTOComparator());
		return responseList;
	}

	private boolean isValidSID(int accountId, String sid) {
		Account account = accountRepository.findById(accountId);
		if (account != null && account.getSid().equals(sid))
			return true;
		return false;
	}

}