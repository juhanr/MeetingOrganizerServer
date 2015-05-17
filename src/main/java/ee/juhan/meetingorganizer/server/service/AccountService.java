package ee.juhan.meetingorganizer.server.service;

import java.util.List;

import ee.juhan.meetingorganizer.server.rest.domain.ContactDTO;

public interface AccountService {

	List<ContactDTO> checkContactsRequest(int accountId, List<ContactDTO> contacts, String sid);

}
