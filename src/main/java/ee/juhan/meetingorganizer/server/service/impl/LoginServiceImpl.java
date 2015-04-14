package ee.juhan.meetingorganizer.server.service.impl;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.juhan.meetingorganizer.server.core.domain.Account;
import ee.juhan.meetingorganizer.server.core.repository.AccountRepository;
import ee.juhan.meetingorganizer.server.core.util.HasherUtil;
import ee.juhan.meetingorganizer.server.rest.domain.ServerResponse;
import ee.juhan.meetingorganizer.server.rest.domain.ServerResult;
import ee.juhan.meetingorganizer.server.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public ServerResponse loginRequest(String email, String password) {
		Account account = accountRepository.findByEmail(email);
		if (account == null)
			return new ServerResponse(ServerResult.NO_ACCOUNT_FOUND);

		try {
			if (HasherUtil.validatePassword(password, account.getHash()))
				return new ServerResponse(ServerResult.SUCCESS,
						account.getSid(), account.getId());
			else
				return new ServerResponse(ServerResult.WRONG_PASSWORD);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
			return new ServerResponse(ServerResult.FAIL);
		}

	}

}
