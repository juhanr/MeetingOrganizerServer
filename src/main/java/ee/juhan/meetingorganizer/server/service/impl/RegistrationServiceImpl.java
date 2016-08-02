package ee.juhan.meetingorganizer.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import ee.juhan.meetingorganizer.server.core.domain.Account;
import ee.juhan.meetingorganizer.server.core.domain.Participant;
import ee.juhan.meetingorganizer.server.core.repository.AccountRepository;
import ee.juhan.meetingorganizer.server.core.repository.ParticipantRepository;
import ee.juhan.meetingorganizer.server.rest.domain.AccountDto;
import ee.juhan.meetingorganizer.server.rest.domain.ServerResponse;
import ee.juhan.meetingorganizer.server.rest.domain.ServerResult;
import ee.juhan.meetingorganizer.server.service.RegistrationService;
import ee.juhan.meetingorganizer.server.util.HasherUtil;
import ee.juhan.meetingorganizer.server.util.SidGeneratorUtil;

@Service
public class RegistrationServiceImpl implements RegistrationService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private ParticipantRepository participantRepository;

	@Override
	public final ServerResponse registration(AccountDto accountDto) {
		if (accountRepository.findByEmail(accountDto.getEmail()) != null) {
			return new ServerResponse(ServerResult.EMAIL_IN_USE);
		}
		if (accountRepository.findByPhoneNumber(accountDto.getPhoneNumber()) != null) {
			return new ServerResponse(ServerResult.PHONE_NUMBER_IN_USE);
		}

		Account account = createAccount(accountDto);
		if (account == null) { return new ServerResponse(ServerResult.FAIL); }
		accountDto.setAccountId(account.getId());
		return new ServerResponse(ServerResult.SUCCESS, account.getSid(), accountDto);
	}

	private Account createAccount(AccountDto accountDto) {
		try {
			String sid = SidGeneratorUtil.generateSid();
			Account account = new Account(accountDto.getName(), accountDto.getEmail(),
					HasherUtil.createHash(accountDto.getPassword()), accountDto.getPhoneNumber(),
					sid);
			accountRepository.save(account);
			updateParticipantObjects(account);
			return account;
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
			return null;
		}
	}

	private void updateParticipantObjects(Account account) {
		List<Participant> participants =
				participantRepository.findByPhoneNumber(account.getPhoneNumber());
		for (Participant participant : participants) {
			participant.setAccount(account);
			participant.setName(account.getName());
			participant.setEmail(account.getEmail());
			participantRepository.save(participant);
		}
	}

}