package ee.juhan.meetingorganizer.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.juhan.meetingorganizer.server.core.domain.Account;
import ee.juhan.meetingorganizer.server.core.repository.AccountRepository;
import ee.juhan.meetingorganizer.server.rest.domain.ContactDTO;
import ee.juhan.meetingorganizer.server.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public List<ContactDTO> checkContactsRequest(int accountId,
			List<ContactDTO> contacts, String sid) {
		if (!isValidSID(accountId, sid))
			return null;
		for (int i = 0; i < contacts.size(); i++) {
			ContactDTO contact = contacts.get(i);
			if (!isWithAreaNumber(contact.getPhoneNumber())) {
				contact.setPhoneNumber(addAreaNumber(contact.getPhoneNumber(),
						accountId));
			}

			Account contactAccount = accountRepository
					.findByPhoneNumber(contact.getPhoneNumber());
			if (contactAccount != null) {
				contact.setAccountId(contactAccount.getId());
			}
			contacts.set(i, contact);
		}
		return contacts;
	}

	private boolean isWithAreaNumber(String phoneNumber) {
		return phoneNumber.substring(0, 1).equals("+");
	}

	private String addAreaNumber(String phoneNumber, int accountId) {
		Account account = accountRepository.findById(accountId);
		if (account != null
				&& account.getPhoneNumber().substring(0, 1).equals("+")) {
			String areaNumber = account.getPhoneNumber().substring(0, 4);
			return areaNumber + phoneNumber;
		}
		return phoneNumber;
	}

	private boolean isValidSID(int accountId, String sid) {
		Account account = accountRepository.findById(accountId);
		if (account != null && account.getSid().equals(sid))
			return true;
		return false;
	}

}