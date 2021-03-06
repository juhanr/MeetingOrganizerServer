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

import ee.juhan.meetingorganizer.server.rest.domain.ContactDto;
import ee.juhan.meetingorganizer.server.service.AccountService;
import ee.juhan.meetingorganizer.server.service.AuthorizationService;

@RestController
@RequestMapping(ControllerConstants.ACCOUNT_PATH)
public class AccountController {

	private static final Logger LOG = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private AccountService accountService;

	@Autowired
	private AuthorizationService authorizationService;

	@RequestMapping(method = RequestMethod.POST,
			value = "/{" + ControllerConstants.ACCOUNT_ID + "}" +
					ControllerConstants.CHECK_CONTACTS_PATH)
	public final ResponseEntity<List<ContactDto>> checkContactsRequest(
			@PathVariable(ControllerConstants.ACCOUNT_ID) int accountId,
			@RequestBody List<ContactDto> contacts,
			@CookieValue(value = ControllerConstants.SID) String sid) {
		LOG.info("Check contacts request: accountId=" + accountId + ", sid=" + sid);
		authorizationService.authorizeAccount(accountId, sid);
		List<ContactDto> response = accountService.checkContacts(accountId, contacts);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

}
