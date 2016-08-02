package ee.juhan.meetingorganizer.server.service;

import ee.juhan.meetingorganizer.server.rest.domain.AccountDto;
import ee.juhan.meetingorganizer.server.rest.domain.ServerResponse;

public interface RegistrationService {

	ServerResponse registration(AccountDto accountDto);

}
