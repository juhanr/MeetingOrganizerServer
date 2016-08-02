package ee.juhan.meetingorganizer.server.service;

import ee.juhan.meetingorganizer.server.rest.domain.AccountDto;
import ee.juhan.meetingorganizer.server.rest.domain.ServerResponse;

public interface LoginService {

	ServerResponse login(AccountDto accountDto);

}