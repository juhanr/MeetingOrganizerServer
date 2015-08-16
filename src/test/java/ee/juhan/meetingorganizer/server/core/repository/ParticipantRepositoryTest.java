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

import javax.transaction.Transactional;

import ee.juhan.meetingorganizer.server.Application;
import ee.juhan.meetingorganizer.server.TestUtil;
import ee.juhan.meetingorganizer.server.core.domain.Participant;

import static org.junit.Assert.assertEquals;
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
	private ParticipantRepository participantRepository;

	@Test
	public void findById() {
		Participant testParticipant = TestUtil.generateTestParticipant(participantRepository);

		Participant participant = participantRepository.findById(testParticipant.getId());
		assertNotNull(participant);
		assertEquals("Participant account id", testParticipant.getAccountId(),
				testParticipant.getAccountId());
		assertEquals("Participant name", testParticipant.getName(), testParticipant.getName());
		assertEquals("Participant email", testParticipant.getEmail(), testParticipant.getEmail());
		assertEquals("Participant phone number", testParticipant.getPhoneNumber(),
				testParticipant.getPhoneNumber());
		assertEquals("Participant answer", testParticipant.getParticipationAnswer(),
				testParticipant.getParticipationAnswer());
		assertEquals("Participant location", testParticipant.getLocation(),
				testParticipant.getLocation());
	}
}
