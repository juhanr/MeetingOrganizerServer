package ee.juhan.meetingorganizer.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.juhan.meetingorganizer.server.core.domain.Account;
import ee.juhan.meetingorganizer.server.core.domain.Meeting;
import ee.juhan.meetingorganizer.server.core.domain.Participant;
import ee.juhan.meetingorganizer.server.core.repository.AccountRepository;
import ee.juhan.meetingorganizer.server.core.repository.MeetingRepository;
import ee.juhan.meetingorganizer.server.core.repository.ParticipantRepository;
import ee.juhan.meetingorganizer.server.rest.domain.MeetingDTO;
import ee.juhan.meetingorganizer.server.rest.domain.ParticipantDTO;
import ee.juhan.meetingorganizer.server.rest.domain.ServerResponse;
import ee.juhan.meetingorganizer.server.rest.domain.ServerResult;
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
	public ServerResponse newMeetingRequest(MeetingDTO meetingDTO) {
		Meeting meeting = createMeeting(meetingDTO);
		meeting = addParticipants(meeting, meetingDTO);
		meetingRepository.save(meeting);
		return new ServerResponse(ServerResult.SUCCESS);
	}

	private Meeting createMeeting(MeetingDTO meetingDTO) {
		Meeting meeting = new Meeting(meetingDTO.getLeaderId(),
				meetingDTO.getTitle(), meetingDTO.getStartTime(),
				meetingDTO.getEndTime(), meetingDTO.getMessage());
		if (meetingDTO.getLocationLatitude() != 0) {
			meeting.setLocationLatitude(meetingDTO.getLocationLatitude());
			meeting.setLocationLongitude(meetingDTO.getLocationLongitude());
		}
		return meeting;
	}

	private Meeting addParticipants(Meeting meeting, MeetingDTO meetingDTO) {
		for (ParticipantDTO participantDTO : meetingDTO.getParticipants()) {
			String phoneNumber = participantDTO.getPhoneNumber();
			if (!phoneNumber.substring(0, 1).equals("+")) {
				phoneNumber = addAreaNumber(phoneNumber, meetingDTO);
			}
			Account account = accountRepository.findByPhoneNumber(phoneNumber);
			Participant participant;
			if (account != null) {
				participant = new Participant(account.getName(),
						account.getEmail(), phoneNumber);
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

	private String addAreaNumber(String phoneNumber, MeetingDTO meetingDTO) {
		Account leaderAccount = accountRepository.findById(meetingDTO
				.getLeaderId());
		if (leaderAccount != null
				&& leaderAccount.getPhoneNumber().substring(0, 1).equals("+")) {
			String areaNumber = leaderAccount.getPhoneNumber().substring(0, 4);
			return areaNumber + phoneNumber;
		}
		return phoneNumber;
	}

	@Override
	public List<MeetingDTO> getOngoingMeetingsRequest(int accountId, String sid) {
		return null;
	}

	@Override
	public List<MeetingDTO> getFutureMeetingsRequest(int accountId, String sid) {
		return null;
	}

	@Override
	public List<MeetingDTO> getPastMeetingsRequest(int accountId, String sid) {
		return null;
	}

	@Override
	public List<MeetingDTO> getInvitationsRequest(int accountId, String sid) {
		return null;
	}

}