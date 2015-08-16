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

import ee.juhan.meetingorganizer.server.rest.domain.ContactDTO;
import ee.juhan.meetingorganizer.server.service.AccountService;

@RestController
@RequestMapping(ControllerConstants.ACCOUNT_PATH)
public class AccountController {

	private static final Logger LOG = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private AccountService accountService;

	@RequestMapping(method = RequestMethod.POST,
			value = "/{" + ControllerConstants.ACCOUNT_ID + "}" +
					ControllerConstants.CHECK_CONTACTS_PATH)
	public final ResponseEntity<List<ContactDTO>> checkContactsRequest(
			@PathVariable(ControllerConstants.ACCOUNT_ID) int accountId,
			@RequestBody List<ContactDTO> contacts,
			@CookieValue(value = ControllerConstants.SID) String sid) {
		LOG.info("Check contacts request: accountId=" + accountId + ", sid=" + sid);
		List<ContactDTO> response = accountService.checkContactsRequest(accountId, contacts, sid);
		LOG.info("Check contacts request completed.");
		if (response == null) { return new ResponseEntity<>(HttpStatus.FORBIDDEN); }
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

}
