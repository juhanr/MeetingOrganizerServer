package ee.juhan.meetingorganizer.server.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ee.juhan.meetingorganizer.server.rest.domain.AccountDTO;
import ee.juhan.meetingorganizer.server.rest.domain.ServerResponse;
import ee.juhan.meetingorganizer.server.service.LoginService;

@RestController
@RequestMapping(ControllerConstants.LOGIN_PATH)
public class LoginController {

	private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private LoginService loginService;

	@RequestMapping(method = RequestMethod.POST)
	public final ResponseEntity<ServerResponse> registrationRequest(
			@RequestBody AccountDTO accountDTO) {
		LOG.info("Log in request: " + accountDTO.getEmail());
		ServerResponse response = loginService.loginRequest(accountDTO);
		LOG.info("Log in request completed.");
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

}