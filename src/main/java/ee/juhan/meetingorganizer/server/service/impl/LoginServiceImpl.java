package ee.juhan.meetingorganizer.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import ee.juhan.meetingorganizer.server.core.domain.Account;
import ee.juhan.meetingorganizer.server.core.repository.AccountRepository;
import ee.juhan.meetingorganizer.server.rest.domain.AccountDto;
import ee.juhan.meetingorganizer.server.rest.domain.ServerResponse;
import ee.juhan.meetingorganizer.server.rest.domain.ServerResult;
import ee.juhan.meetingorganizer.server.service.LoginService;
import ee.juhan.meetingorganizer.server.util.HasherUtil;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public final ServerResponse login(AccountDto accountDto) {
		Account account = accountRepository.findByEmail(accountDto.getEmail());
		if (account == null) { return new ServerResponse(ServerResult.NO_ACCOUNT_FOUND); }
		try {
			if (HasherUtil.validatePassword(accountDto.getPassword(), account.getHash())) {
				accountDto.setName(account.getName());
				accountDto.setPhoneNumber(account.getPhoneNumber());
				accountDto.setAccountId(account.getId());
				return new ServerResponse(ServerResult.SUCCESS, account.getSid(), accountDto);
			} else { return new ServerResponse(ServerResult.WRONG_PASSWORD); }
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
			return new ServerResponse(ServerResult.FAIL);
		}
	}

}
