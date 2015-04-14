package ee.juhan.meetingorganizer.server.service.impl;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.juhan.meetingorganizer.server.core.domain.Account;
import ee.juhan.meetingorganizer.server.core.repository.AccountRepository;
import ee.juhan.meetingorganizer.server.core.util.HasherUtil;
import ee.juhan.meetingorganizer.server.core.util.SIDGeneratorUtil;
import ee.juhan.meetingorganizer.server.rest.domain.ServerResponse;
import ee.juhan.meetingorganizer.server.rest.domain.ServerResult;
import ee.juhan.meetingorganizer.server.service.RegistrationService;

@Service
public class RegistrationServiceImpl implements RegistrationService {

	private static Logger LOG = LoggerFactory
			.getLogger(RegistrationServiceImpl.class);

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public ServerResponse registrationRequest(String email, String password) {
		if (checkEmail(email) == ServerResult.EMAIL_IN_USE)
			return new ServerResponse(ServerResult.EMAIL_IN_USE);
		Account account = createAccount(email, password);
		if (account == null)
			return new ServerResponse(ServerResult.FAIL);
		return new ServerResponse(ServerResult.SUCCESS, account.getSid(),
				account.getId());
	}

	private ServerResult checkEmail(String email) {
		LOG.info("Checking email: " + email);
		Account account = accountRepository.findByEmail(email);
		if (account == null)
			return ServerResult.SUCCESS;
		else
			return ServerResult.EMAIL_IN_USE;
	}

	private Account createAccount(String email, String password) {
		try {
			LOG.info("Creating account: " + email);
			String sid = SIDGeneratorUtil.generateSID();
			Account account = new Account(email,
					HasherUtil.createHash(password), sid);
			accountRepository.save(account);
			return account;
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
			return null;
		}
	}

}
