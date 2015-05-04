package ee.juhan.meetingorganizer.server.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ee.juhan.meetingorganizer.server.rest.domain.MeetingDTO;
import ee.juhan.meetingorganizer.server.rest.domain.ParticipantDTO;
import ee.juhan.meetingorganizer.server.service.MeetingService;

@RestController
@RequestMapping("/meeting")
public class MeetingController {

	private static Logger LOG = LoggerFactory
			.getLogger(MeetingController.class);

	@Autowired
	MeetingService meetingService;

	@RequestMapping(method = RequestMethod.POST, value = "/new")
	public ResponseEntity<MeetingDTO> newMeetingRequest(
			@RequestBody MeetingDTO meetingDTO,
			@RequestHeader(value = "Client-Time-Zone") String clientTimeZone,
			@CookieValue(value = "sid") String sid) {
		LOG.info("New meeting request: " + meetingDTO.getTitle());
		MeetingDTO response = meetingService.newMeetingRequest(meetingDTO,
				clientTimeZone, sid);
		LOG.info("New meeting request completed.");
		if (response == null)
			return new ResponseEntity<MeetingDTO>(HttpStatus.FORBIDDEN);
		return new ResponseEntity<MeetingDTO>(response, HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/ongoing-meetings/account/{accountId}")
	public ResponseEntity<List<MeetingDTO>> getOngoingMeetingsRequest(
			@PathVariable("accountId") String accountId,
			@RequestHeader(value = "Client-Time-Zone") String clientTimeZone,
			@CookieValue(value = "sid") String sid) {
		LOG.info("Get ongoing meetings request for user " + accountId);
		List<MeetingDTO> response = meetingService.getOngoingMeetingsRequest(
				Integer.parseInt(accountId), clientTimeZone, sid);
		LOG.info("Get ongoing meetings request completed.");
		if (response == null)
			return new ResponseEntity<List<MeetingDTO>>(HttpStatus.FORBIDDEN);
		return new ResponseEntity<List<MeetingDTO>>(response, HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/future-meetings/account/{accountId}")
	public ResponseEntity<List<MeetingDTO>> getFutureMeetingsRequest(
			@PathVariable("accountId") String accountId,
			@RequestHeader(value = "Client-Time-Zone") String clientTimeZone,
			@CookieValue(value = "sid") String sid) {
		LOG.info("Get future meetings request for user " + accountId);
		List<MeetingDTO> response = meetingService.getFutureMeetingsRequest(
				Integer.parseInt(accountId), clientTimeZone, sid);
		LOG.info("Get future meetings request completed.");
		if (response == null)
			return new ResponseEntity<List<MeetingDTO>>(HttpStatus.FORBIDDEN);
		return new ResponseEntity<List<MeetingDTO>>(response, HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/past-meetings/account/{accountId}")
	public ResponseEntity<List<MeetingDTO>> getPastMeetingsRequest(
			@PathVariable("accountId") String accountId,
			@RequestHeader(value = "Client-Time-Zone") String clientTimeZone,
			@CookieValue(value = "sid") String sid) {
		LOG.info("Get past meetings request for user " + accountId);
		List<MeetingDTO> response = meetingService.getPastMeetingsRequest(
				Integer.parseInt(accountId), clientTimeZone, sid);
		LOG.info("Get past meetings request completed.");
		if (response == null)
			return new ResponseEntity<List<MeetingDTO>>(HttpStatus.FORBIDDEN);
		return new ResponseEntity<List<MeetingDTO>>(response, HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/invitations/account/{accountId}")
	public ResponseEntity<List<MeetingDTO>> getInvitationsRequest(
			@PathVariable("accountId") String accountId,
			@RequestHeader(value = "Client-Time-Zone") String clientTimeZone,
			@CookieValue(value = "sid") String sid) {
		LOG.info("Get invitations request for user " + accountId);
		List<MeetingDTO> response = meetingService.getInvitationsRequest(
				Integer.parseInt(accountId), clientTimeZone, sid);
		LOG.info("Get invitations request completed.");
		if (response == null)
			return new ResponseEntity<List<MeetingDTO>>(HttpStatus.FORBIDDEN);
		return new ResponseEntity<List<MeetingDTO>>(response, HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.POST, value = "/{meetingId}/update-participant")
	public ResponseEntity<MeetingDTO> updateParticipantRequest(
			@RequestBody ParticipantDTO participantDTO,
			@PathVariable("meetingId") String meetingId,
			@RequestHeader(value = "Client-Time-Zone") String clientTimeZone,
			@CookieValue(value = "sid") String sid) {
		LOG.info("Update participant request for participant " + participantDTO);
		MeetingDTO response = meetingService.updateParticipantRequest(
				participantDTO, Integer.parseInt(meetingId), clientTimeZone,
				sid);
		LOG.info("Update participant request completed.");
		if (response == null)
			return new ResponseEntity<MeetingDTO>(HttpStatus.FORBIDDEN);
		return new ResponseEntity<MeetingDTO>(response, HttpStatus.OK);

	}

}
