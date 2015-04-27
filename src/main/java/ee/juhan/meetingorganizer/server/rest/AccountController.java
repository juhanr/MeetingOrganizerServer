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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

	@RequestMapping(method = RequestMethod.POST, value = "/{id}/check-contacts")
	public ResponseEntity<List<ContactDTO>> CheckContactsRequest(
			@PathVariable("id") String accountId,
			@RequestBody List<ContactDTO> contacts,
			@CookieValue(value = "sid") String sid) {
		LOG.info("Check contacts request: accountId=" + accountId + ", sid="
				+ sid);
		List<ContactDTO> response = accountService.checkContactsRequest(
				Integer.parseInt(accountId), contacts, sid);
		LOG.info("Check contacts request completed.");
		if (response == null)
			return new ResponseEntity<List<ContactDTO>>(HttpStatus.FORBIDDEN);
		return new ResponseEntity<List<ContactDTO>>(response, HttpStatus.OK);

	}

}
