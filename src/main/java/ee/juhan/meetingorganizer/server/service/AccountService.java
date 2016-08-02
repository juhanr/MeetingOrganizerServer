package ee.juhan.meetingorganizer.server.service;

import java.util.List;

import ee.juhan.meetingorganizer.server.rest.domain.ContactDto;

public interface AccountService {

	List<ContactDto> checkContacts(int accountId, List<ContactDto> contacts, String sid);

}
