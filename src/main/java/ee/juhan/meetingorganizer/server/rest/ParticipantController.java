package ee.juhan.meetingorganizer.server.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ee.juhan.meetingorganizer.server.rest.domain.ParticipationAnswer;
import ee.juhan.meetingorganizer.server.rest.domain.SendGpsLocationAnswer;
import ee.juhan.meetingorganizer.server.service.AuthorizationService;
import ee.juhan.meetingorganizer.server.service.ParticipantService;

@RestController
@RequestMapping(ControllerConstants.PARTICIPANT_PATH)
public class ParticipantController {

	private static final Logger LOG = LoggerFactory.getLogger(ParticipantController.class);

	@Autowired
	private ParticipantService participantService;

	@Autowired
	private AuthorizationService authorizationService;

	@RequestMapping(method = RequestMethod.POST,
			value = "/{" + ControllerConstants.PARTICIPANT_ID + "}" +
					ControllerConstants.UPDATE_PARTICIPATION_ANSWER_PATH)
	@ResponseStatus(HttpStatus.OK)
	public final void updateParticipationAnswerRequest(
			@RequestBody ParticipationAnswer participationAnswer,
			@PathVariable(ControllerConstants.PARTICIPANT_ID) int participantId,
			@CookieValue(value = ControllerConstants.SID) String sid) {
		LOG.info("Update participation answer request for participant " + participantId);
		authorizationService.authorizeParticipant(participantId, sid);
		participantService.updateParticipationAnswer(participantId, participationAnswer);
	}

	@RequestMapping(method = RequestMethod.POST,
			value = "/{" + ControllerConstants.PARTICIPANT_ID + "}" +
					ControllerConstants.UPDATE_SEND_LOCATION_ANSWER_PATH)
	@ResponseStatus(HttpStatus.OK)
	public final void updateSendGpsLocationAnswer(
			@RequestBody SendGpsLocationAnswer sendGpsLocationAnswer,
			@PathVariable(ControllerConstants.PARTICIPANT_ID) int participantId,
			@CookieValue(value = ControllerConstants.SID) String sid) {
		LOG.info("Update participant location request for participant " + participantId);
		authorizationService.authorizeParticipant(participantId, sid);
		participantService.updateSendGpsLocationAnswer(participantId, sendGpsLocationAnswer);
	}

}
