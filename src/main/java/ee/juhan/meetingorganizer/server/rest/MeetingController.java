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

import ee.juhan.meetingorganizer.server.rest.domain.MeetingDto;
import ee.juhan.meetingorganizer.server.service.AuthorizationService;
import ee.juhan.meetingorganizer.server.service.MeetingService;

@RestController
@RequestMapping(ControllerConstants.MEETING_PATH)
public class MeetingController {

	private static final Logger LOG = LoggerFactory.getLogger(MeetingController.class);

	@Autowired
	private MeetingService meetingService;

	@Autowired
	private AuthorizationService authorizationService;

	@RequestMapping(method = RequestMethod.POST, value = ControllerConstants.NEW_PATH)
	public final ResponseEntity<MeetingDto> newMeetingRequest(@RequestBody MeetingDto meetingDto,
			@CookieValue(value = ControllerConstants.SID) String sid) {
		LOG.info("New meeting request: " + meetingDto.getTitle());
		authorizationService.authorizeAccount(meetingDto.getLeaderId(), sid);
		MeetingDto response = meetingService.newMeeting(meetingDto);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET,
			value = "/{" + ControllerConstants.MEETINGS_TYPE + "}" +
					ControllerConstants.ACCOUNT_PATH + "/{" +
					ControllerConstants.ACCOUNT_ID + "}")
	public final ResponseEntity<List<MeetingDto>> getMeetingsRequest(
			@PathVariable(ControllerConstants.MEETINGS_TYPE) String meetingsType,
			@PathVariable(ControllerConstants.ACCOUNT_ID) int accountId,
			@CookieValue(value = ControllerConstants.SID) String sid) {
		LOG.info("Get meetings request for user " + accountId);
		authorizationService.authorizeAccount(accountId, sid);
		List<MeetingDto> response = null;
		switch (meetingsType) {
			case ControllerConstants.ACTIVE_MEETINGS:
				response = meetingService.getActiveMeetings(accountId);
				break;
			case ControllerConstants.PAST_MEETINGS:
				response = meetingService.getPastMeetings(accountId);
				break;
			case ControllerConstants.INVITATIONS:
				response = meetingService.getInvitations(accountId);
				break;
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST,
			value = "/{" + ControllerConstants.MEETING_ID + "}" +
					ControllerConstants.GENERATE_RECOMMENDED_LOCATIONS_PATH)
	public final ResponseEntity<MeetingDto> generateRecommendedLocationsRequest(
			@PathVariable(ControllerConstants.MEETING_ID) int meetingId,
			@CookieValue(value = ControllerConstants.SID) String sid) {
		LOG.info("Generate recommended locations request for meeting " + meetingId);
		authorizationService.authorizeMeetingLeader(meetingId, sid);
		MeetingDto response = meetingService.generateRecommendedLocations(meetingId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
