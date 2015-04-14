package ee.juhan.meetingorganizer.server.service;

import ee.juhan.meetingorganizer.server.rest.domain.AccountDTO;
import ee.juhan.meetingorganizer.server.rest.domain.ServerResponse;

public interface RegistrationService {

	public ServerResponse registrationRequest(AccountDTO accountDTO);

}
