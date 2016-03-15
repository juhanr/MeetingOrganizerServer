package ee.juhan.meetingorganizer.server.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import ee.juhan.meetingorganizer.server.rest.domain.MeetingDTO;
import ee.juhan.meetingorganizer.server.rest.domain.ParticipantDTO;
import ee.juhan.meetingorganizer.server.service.MeetingService;

@RestController
@RequestMapping(ControllerConstants.MEETING_PATH)
public class MeetingController {

	private static final Logger LOG = LoggerFactory.getLogger(MeetingController.class);

	@Autowired
	private MeetingService meetingService;

	@RequestMapping(method = RequestMethod.POST, value = ControllerConstants.NEW_PATH)
	public final ResponseEntity<MeetingDTO> newMeetingRequest(@RequestBody MeetingDTO meetingDTO,
			@CookieValue(value = ControllerConstants.SID) String sid) {
		LOG.info("New meeting request: " + meetingDTO.getTitle());
		MeetingDTO response = meetingService.newMeetingRequest(meetingDTO, sid);
		LOG.info("New meeting request completed.");
		if (response == null) { return new ResponseEntity<>(HttpStatus.FORBIDDEN); }
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.GET,
			value = "/{" + ControllerConstants.MEETINGS_TYPE + "}" +
					ControllerConstants.ACCOUNT_PATH + "/{" +
					ControllerConstants.ACCOUNT_ID + "}")
	public final ResponseEntity<List<MeetingDTO>> getMeetingsRequest(
			@PathVariable(ControllerConstants.MEETINGS_TYPE) String meetingsType,
			@PathVariable(ControllerConstants.ACCOUNT_ID) int accountId,
			@CookieValue(value = ControllerConstants.SID) String sid) {
		LOG.info("Get meetings request for user " + accountId);
		List<MeetingDTO> response = null;
		switch (meetingsType) {
			case ControllerConstants.ACTIVE_MEETINGS:
				response = meetingService.getActiveMeetingsRequest(accountId, sid);
				break;
			case ControllerConstants.PAST_MEETINGS:
				response = meetingService.getPastMeetingsRequest(accountId, sid);
				break;
			case ControllerConstants.INVITATIONS:
				response = meetingService.getInvitationsRequest(accountId, sid);
				break;
		}
		LOG.info("Get meetings request completed.");
		if (response == null) { return new ResponseEntity<>(HttpStatus.FORBIDDEN); }
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.POST,
			value = "/{" + ControllerConstants.MEETING_ID + "}" +
					ControllerConstants.UPDATE_PARTICIPANT_PATH)
	public final ResponseEntity<MeetingDTO> updateParticipantRequest(
			@RequestBody ParticipantDTO participantDTO,
			@PathVariable(ControllerConstants.MEETING_ID) int meetingId,
			@CookieValue(value = ControllerConstants.SID) String sid) {
		LOG.info("Update participant request for participant " + participantDTO);
		MeetingDTO response =
				meetingService.updateParticipantRequest(participantDTO, meetingId, sid);
		LOG.info("Update participant request completed.");
		if (response == null) { return new ResponseEntity<>(HttpStatus.FORBIDDEN); }
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

}
