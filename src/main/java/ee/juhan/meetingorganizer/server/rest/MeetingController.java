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
		MeetingDTO response = meetingService.newMeeting(meetingDTO, sid);
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
				response = meetingService.getActiveMeetings(accountId, sid);
				break;
			case ControllerConstants.PAST_MEETINGS:
				response = meetingService.getPastMeetings(accountId, sid);
				break;
			case ControllerConstants.INVITATIONS:
				response = meetingService.getInvitations(accountId, sid);
				break;
		}
		if (response == null) { return new ResponseEntity<>(HttpStatus.FORBIDDEN); }
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST,
			value = "/{" + ControllerConstants.MEETING_ID + "}" +
					ControllerConstants.UPDATE_PARTICIPATION_ANSWER_PATH)
	public final ResponseEntity<MeetingDTO> updateParticipationAnswerRequest(
			@RequestBody ParticipantDTO participantDTO,
			@PathVariable(ControllerConstants.MEETING_ID) int meetingId,
			@CookieValue(value = ControllerConstants.SID) String sid) {
		LOG.info("Update participation answer request for participant " + participantDTO);
		MeetingDTO response =
				meetingService.updateParticipationAnswer(participantDTO, meetingId, sid);
		if (response == null) { return new ResponseEntity<>(HttpStatus.FORBIDDEN); }
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST,
			value = "/{" + ControllerConstants.MEETING_ID + "}" +
					ControllerConstants.UPDATE_PARTICIPANT_LOCATION_PATH)
	public final ResponseEntity<MeetingDTO> updateParticipantLocationRequest(
			@RequestBody ParticipantDTO participantDTO,
			@PathVariable(ControllerConstants.MEETING_ID) int meetingId,
			@CookieValue(value = ControllerConstants.SID) String sid) {
		LOG.info("Update participant location request for participant " + participantDTO);
		MeetingDTO response =
				meetingService.updateParticipantLocation(participantDTO, meetingId, sid);
		if (response == null) { return new ResponseEntity<>(HttpStatus.FORBIDDEN); }
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST,
			value = "/{" + ControllerConstants.MEETING_ID + "}" +
					ControllerConstants.GENERATE_RECOMMENDED_LOCATIONS_PATH)
	public final ResponseEntity<MeetingDTO> generateRecommendedLocationsRequest(
			@PathVariable(ControllerConstants.MEETING_ID) int meetingId,
			@CookieValue(value = ControllerConstants.SID) String sid) {
		LOG.info("Generate recommended locations request for meeting " + meetingId);
		MeetingDTO response = meetingService.generateRecommendedLocations(meetingId, sid);
		if (response == null) { return new ResponseEntity<>(HttpStatus.FORBIDDEN); }
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
