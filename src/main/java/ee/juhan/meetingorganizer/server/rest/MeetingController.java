package ee.juhan.meetingorganizer.server.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.juhan.meetingorganizer.server.rest.domain.MeetingDTO;
import ee.juhan.meetingorganizer.server.rest.domain.ServerResult;
import ee.juhan.meetingorganizer.server.service.MeetingService;

@RestController
@RequestMapping("/meeting")
public class MeetingController {

	private static Logger LOG = LoggerFactory
			.getLogger(MeetingController.class);

	@Autowired
	MeetingService meetingService;

	@RequestMapping(method = RequestMethod.POST, value = "/new")
	public ResponseEntity<ServerResult> newMeetingRequest(
			@RequestBody MeetingDTO meetingDTO,
			@CookieValue(value = "sid", defaultValue = "cookie") String cookie) {
		LOG.info("New meeting request: " + meetingDTO.getTitle());
		ServerResult response = meetingService.newMeetingRequest(meetingDTO,
				cookie);
		LOG.info("New meeting request completed.");
		if (response == null)
			return new ResponseEntity<ServerResult>(HttpStatus.FORBIDDEN);
		return new ResponseEntity<ServerResult>(response, HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/ongoing-meetings")
	public ResponseEntity<List<MeetingDTO>> getOngoingMeetingsRequest(
			@RequestParam(value = "accountId") String accountId,
			@RequestParam(value = "clientTimeZone") String clientTimeZone,
			@CookieValue(value = "sid", defaultValue = "cookie") String cookie) {
		LOG.info("Get ongoing meetings request for user " + accountId);
		List<MeetingDTO> response = meetingService.getOngoingMeetingsRequest(
				Integer.parseInt(accountId), clientTimeZone, cookie);
		LOG.info("Get ongoing meetings request completed.");
		if (response == null)
			return new ResponseEntity<List<MeetingDTO>>(HttpStatus.FORBIDDEN);
		return new ResponseEntity<List<MeetingDTO>>(response, HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/future-meetings")
	public ResponseEntity<List<MeetingDTO>> getFutureMeetingsRequest(
			@RequestParam(value = "accountId") String accountId,
			@RequestParam(value = "clientTimeZone") String clientTimeZone,
			@CookieValue(value = "sid", defaultValue = "cookie") String cookie) {
		LOG.info("Get future meetings request for user " + accountId);
		List<MeetingDTO> response = meetingService.getFutureMeetingsRequest(
				Integer.parseInt(accountId), clientTimeZone, cookie);
		LOG.info("Get future meetings request completed.");
		if (response == null)
			return new ResponseEntity<List<MeetingDTO>>(HttpStatus.FORBIDDEN);
		return new ResponseEntity<List<MeetingDTO>>(response, HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/past-meetings")
	public ResponseEntity<List<MeetingDTO>> getPastMeetingsRequest(
			@RequestParam(value = "accountId") String accountId,
			@RequestParam(value = "clientTimeZone") String clientTimeZone,
			@CookieValue(value = "sid", defaultValue = "cookie") String cookie) {
		LOG.info("Get past meetings request for user " + accountId);
		List<MeetingDTO> response = meetingService.getPastMeetingsRequest(
				Integer.parseInt(accountId), clientTimeZone, cookie);
		LOG.info("Get past meetings request completed.");
		if (response == null)
			return new ResponseEntity<List<MeetingDTO>>(HttpStatus.FORBIDDEN);
		return new ResponseEntity<List<MeetingDTO>>(response, HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/invitations")
	public ResponseEntity<List<MeetingDTO>> getInvitationsRequest(
			@RequestParam(value = "accountId") String accountId,
			@RequestParam(value = "clientTimeZone") String clientTimeZone,
			@CookieValue(value = "sid", defaultValue = "cookie") String cookie) {
		LOG.info("Get invitations request for user " + accountId);
		List<MeetingDTO> response = meetingService.getInvitationsRequest(
				Integer.parseInt(accountId), clientTimeZone, cookie);
		LOG.info("Get invitations request completed.");
		if (response == null)
			return new ResponseEntity<List<MeetingDTO>>(HttpStatus.FORBIDDEN);
		return new ResponseEntity<List<MeetingDTO>>(response, HttpStatus.OK);

	}

}
