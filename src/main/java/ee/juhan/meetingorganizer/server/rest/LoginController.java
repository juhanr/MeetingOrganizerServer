package ee.juhan.meetingorganizer.server.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.juhan.meetingorganizer.server.rest.domain.ServerResponse;
import ee.juhan.meetingorganizer.server.service.LoginService;

@RestController
@RequestMapping("/login")
public class LoginController {

	private static Logger LOG = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	LoginService loginService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<ServerResponse> registrationRequest(
			@RequestParam(value = "email") String email,
			@RequestParam(value = "password") String password) {
		LOG.info("Log in request: " + email);
		ServerResponse response = loginService.loginRequest(email, password);
		LOG.info("Log in request completed.");
		return new ResponseEntity<ServerResponse>(response, HttpStatus.OK);

	}

}