package ee.juhan.meetingorganizer.server.service;

import ee.juhan.meetingorganizer.server.rest.domain.AccountDTO;
import ee.juhan.meetingorganizer.server.rest.domain.ServerResponse;

public interface LoginService {

	public ServerResponse loginRequest(AccountDTO accountDTO);

}