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
import ee.juhan.meetingorganizer.server.rest.domain.ServerResponse;
import ee.juhan.meetingorganizer.server.service.MeetingService;

@RestController
@RequestMapping("/meeting")
public class MeetingController {

	private static Logger LOG = LoggerFactory
			.getLogger(MeetingController.class);

	@Autowired
	MeetingService meetingService;

	@RequestMapping(method = RequestMethod.POST, value = "/new")
	public ResponseEntity<ServerResponse> newMeetingRequest(
			@RequestBody MeetingDTO meetingDTO) {
		LOG.info("New meeting request: " + meetingDTO.getTitle());
		ServerResponse response = meetingService.newMeetingRequest(meetingDTO);
		LOG.info("New meeting request completed.");
		return new ResponseEntity<ServerResponse>(response, HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/ongoing-meetings")
	public ResponseEntity<List<MeetingDTO>> getOngoingMeetingsRequest(
			@RequestParam(value = "userId") String userId,
			@CookieValue(value = "sid", defaultValue = "cookie") String cookie) {
		LOG.info("Get ongoing meetings request for user " + userId);
		if (cookie.equals("")) {
			return new ResponseEntity<List<MeetingDTO>>(HttpStatus.FORBIDDEN);
		}
		List<MeetingDTO> ongoingMeetings = meetingService
				.getOngoingMeetingsRequest(Integer.parseInt(userId), cookie);
		LOG.info("Get ongoing meetings request completed.");
		return new ResponseEntity<List<MeetingDTO>>(ongoingMeetings,
				HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/future-meetings")
	public ResponseEntity<List<MeetingDTO>> getFutureMeetingsRequest(
			@RequestParam(value = "userId") String userId,
			@CookieValue(value = "sid", defaultValue = "cookie") String cookie) {
		LOG.info("Get future meetings request for user " + userId);
		if (cookie.equals("")) {
			return new ResponseEntity<List<MeetingDTO>>(HttpStatus.FORBIDDEN);
		}
		List<MeetingDTO> futureMeetings = meetingService
				.getFutureMeetingsRequest(Integer.parseInt(userId), cookie);
		LOG.info("Get future meetings request completed.");
		return new ResponseEntity<List<MeetingDTO>>(futureMeetings,
				HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/past-meetings")
	public ResponseEntity<List<MeetingDTO>> getPastMeetingsRequest(
			@RequestParam(value = "userId") String userId,
			@CookieValue(value = "sid", defaultValue = "cookie") String cookie) {
		LOG.info("Get past meetings request for user " + userId);
		if (cookie.equals("")) {
			return new ResponseEntity<List<MeetingDTO>>(HttpStatus.FORBIDDEN);
		}
		List<MeetingDTO> pastMeetings = meetingService.getPastMeetingsRequest(
				Integer.parseInt(userId), cookie);
		LOG.info("Get past meetings request completed.");
		return new ResponseEntity<List<MeetingDTO>>(pastMeetings, HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/invitations")
	public ResponseEntity<List<MeetingDTO>> getInvitationsRequest(
			@RequestParam(value = "userId") String userId,
			@CookieValue(value = "sid", defaultValue = "cookie") String cookie) {
		LOG.info("Get invitations request for user " + userId);
		if (cookie.equals("")) {
			return new ResponseEntity<List<MeetingDTO>>(HttpStatus.FORBIDDEN);
		}
		List<MeetingDTO> invitations = meetingService.getInvitationsRequest(
				Integer.parseInt(userId), cookie);
		LOG.info("Get invitations request completed.");
		return new ResponseEntity<List<MeetingDTO>>(invitations, HttpStatus.OK);

	}

}
