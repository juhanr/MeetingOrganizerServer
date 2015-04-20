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

import ee.juhan.meetingorganizer.server.rest.domain.ContactDTO;
import ee.juhan.meetingorganizer.server.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {

	private static Logger LOG = LoggerFactory
			.getLogger(AccountController.class);

	@Autowired
	AccountService accountService;

	@RequestMapping(method = RequestMethod.POST, value = "/check-contacts")
	public ResponseEntity<List<ContactDTO>> CheckContactsRequest(
			@RequestParam(value = "accountId") String accountId,
			@RequestBody List<ContactDTO> contacts,
			@CookieValue(value = "sid", defaultValue = "cookie") String cookie) {
		LOG.info("Check contacts request: " + accountId);
		List<ContactDTO> response = accountService.checkContactsRequest(
				Integer.parseInt(accountId), contacts, cookie);
		LOG.info("Check contacts request completed.");
		if (response == null)
			return new ResponseEntity<List<ContactDTO>>(HttpStatus.FORBIDDEN);
		return new ResponseEntity<List<ContactDTO>>(response, HttpStatus.OK);

	}

}
