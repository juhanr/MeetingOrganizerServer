package ee.juhan.meetingorganizer.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import ee.juhan.meetingorganizer.server.core.domain.Account;
import ee.juhan.meetingorganizer.server.core.repository.AccountRepository;
import ee.juhan.meetingorganizer.server.rest.domain.ContactDto;
import ee.juhan.meetingorganizer.server.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	private static final int AREA_CODE_LENGTH = 3;

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public final List<ContactDto> checkContacts(int accountId, List<ContactDto> contacts,
			String sid) {
		if (!isValidSID(accountId, sid)) { return null; }
		for (int i = 0; i < contacts.size(); i++) {
			ContactDto contact = contacts.get(i);
			if (contact.getPhoneNumber() != null && !contact.getPhoneNumber().equals("")) {
				if (!isWithAreaNumber(contact.getPhoneNumber())) {
					contact.setPhoneNumber(addAreaNumber(contact.getPhoneNumber(), accountId));
				}

				Account contactAccount =
						accountRepository.findByPhoneNumber(contact.getPhoneNumber());
				if (contactAccount != null) {
					contact.setAccountId(contactAccount.getId());
				}
				contacts.set(i, contact);
			}
		}
		return contacts;
	}

	private boolean isWithAreaNumber(String phoneNumber) {
		return phoneNumber.substring(0, 1).equals("+");
	}

	private String addAreaNumber(String phoneNumber, int accountId) {
		Account account = accountRepository.findById(accountId);
		if (account != null && account.getPhoneNumber().substring(0, 1).equals("+")) {
			String areaNumber = account.getPhoneNumber().substring(0, AREA_CODE_LENGTH + 1);
			return areaNumber + phoneNumber;
		}
		return phoneNumber;
	}

	private boolean isValidSID(int accountId, String sid) {
		Account account = accountRepository.findById(accountId);
		return account != null && account.getSid().equals(sid);
	}

}
