package ee.juhan.meetingorganizer.server.core.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import javax.transaction.Transactional;

import ee.juhan.meetingorganizer.server.Application;
import ee.juhan.meetingorganizer.server.TestUtil;
import ee.juhan.meetingorganizer.server.core.domain.Account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringApplicationConfiguration(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@TestExecutionListeners(
		{DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class AccountRepositoryTest {

	private static final Logger LOG = LoggerFactory.getLogger(TestUtil.class);
	private static Account testAccount;
	private static boolean isDatabaseSetUp = false;
	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private MeetingRepository meetingRepository;

	@Test
	public void findById() {
		Account testAccount = TestUtil.generateTestAccount(accountRepository);
		assertNotNull(testAccount);
		Account account = accountRepository.findById(testAccount.getId());
		assertNotNull(account);
		assertEquals("Account name", testAccount.getName(), account.getName());
		assertEquals("Account email", testAccount.getEmail(), account.getEmail());
		assertEquals("Account phone number", testAccount.getPhoneNumber(),
				account.getPhoneNumber());
	}

	@Test
	public void findMeetingsById() {
		testAccount = TestUtil.generateTestAccount(accountRepository);
		testAccount.addMeeting(TestUtil.generateTestMeeting(meetingRepository));
		testAccount.addMeeting(TestUtil.generateTestMeeting(meetingRepository));
		accountRepository.save(testAccount);

		Account account = accountRepository.findById(testAccount.getId());
		assertNotNull(account);
	}

}