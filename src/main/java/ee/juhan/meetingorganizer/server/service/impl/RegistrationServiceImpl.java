package ee.juhan.meetingorganizer.server.service.impl;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.juhan.meetingorganizer.server.core.domain.Account;
import ee.juhan.meetingorganizer.server.core.repository.AccountRepository;
import ee.juhan.meetingorganizer.server.core.util.HasherUtil;
import ee.juhan.meetingorganizer.server.core.util.SIDGeneratorUtil;
import ee.juhan.meetingorganizer.server.rest.domain.AccountDTO;
import ee.juhan.meetingorganizer.server.rest.domain.ServerResponse;
import ee.juhan.meetingorganizer.server.rest.domain.ServerResult;
import ee.juhan.meetingorganizer.server.service.RegistrationService;

@Service
public class RegistrationServiceImpl implements RegistrationService {

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public ServerResponse registrationRequest(AccountDTO accountDTO) {
		if (accountRepository.findByEmail(accountDTO.getEmail()) != null)
			return new ServerResponse(ServerResult.EMAIL_IN_USE);
		if (accountRepository.findByPhoneNumber(accountDTO.getPhoneNumber()) != null)
			return new ServerResponse(ServerResult.PHONE_NUMBER_IN_USE);

		Account account = createAccount(accountDTO);
		if (account == null)
			return new ServerResponse(ServerResult.FAIL);
		return new ServerResponse(ServerResult.SUCCESS, account.getSid(),
				account.getId());
	}

	private Account createAccount(AccountDTO accountDTO) {
		try {
			String sid = SIDGeneratorUtil.generateSID();
			Account account = new Account(accountDTO.getName(),
					accountDTO.getEmail(), HasherUtil.createHash(accountDTO
							.getPassword()), accountDTO.getPhoneNumber(), sid);
			accountRepository.save(account);
			return account;
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
			return null;
		}
	}

}
