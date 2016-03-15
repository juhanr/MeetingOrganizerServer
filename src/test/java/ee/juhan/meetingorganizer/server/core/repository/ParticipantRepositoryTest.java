package ee.juhan.meetingorganizer.server.core.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.List;

import javax.transaction.Transactional;

import ee.juhan.meetingorganizer.server.Application;
import ee.juhan.meetingorganizer.server.TestUtil;
import ee.juhan.meetingorganizer.server.core.domain.Account;
import ee.juhan.meetingorganizer.server.core.domain.Meeting;
import ee.juhan.meetingorganizer.server.core.domain.Participant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@SpringApplicationConfiguration(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@TestExecutionListeners(
		{DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class ParticipantRepositoryTest {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private MeetingRepository meetingRepository;

	@Autowired
	private ParticipantRepository participantRepository;

	@Test
	public void findParticipantsByMeetingId() {
		Meeting testMeeting = TestUtil.generateTestMeeting(meetingRepository, 1);
		Account testAccount1 = TestUtil.generateTestAccount(accountRepository);
		Account testAccount2 = TestUtil.generateTestAccount(accountRepository);
		Participant testParticipant1 =
				TestUtil.generateTestParticipant(participantRepository, testAccount1, testMeeting);
		Participant testParticipant2 =
				TestUtil.generateTestParticipant(participantRepository, testAccount2, testMeeting);
		List<Participant> participants = participantRepository.findParticipantsByMeetingId(1);
		assertNotNull(participants);
		assertEquals("Participants list right size", participants.size(), 2);
		assertEquals(participants.get(0), testParticipant1);
		assertEquals(participants.get(1), testParticipant2);
	}

	@Test
	public void findMeetingByPhoneNumber() {
		Account testAccount = TestUtil.generateTestAccount(accountRepository);
		assertNotNull(testAccount);
		Meeting testMeeting = TestUtil.generateTestMeeting(meetingRepository, testAccount.getId());
		Participant testParticipant =
				TestUtil.generateTestParticipant(participantRepository, testAccount, testMeeting);

		List<Meeting> meetings =
				participantRepository.findMeetingByPhoneNumber(testParticipant.getPhoneNumber());
		assertFalse("Meetings list empty", meetings.isEmpty());
	}
}
