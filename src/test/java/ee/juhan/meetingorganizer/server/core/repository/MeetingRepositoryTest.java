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
import ee.juhan.meetingorganizer.server.core.domain.Meeting;
import ee.juhan.meetingorganizer.server.core.domain.Participant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@SpringApplicationConfiguration(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@TestExecutionListeners(
		{DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class MeetingRepositoryTest {

	@Autowired
	private MeetingRepository meetingRepository;

	@Autowired
	private ParticipantRepository participantRepository;

	@Test
	public void findById() {
		Meeting testMeeting = TestUtil.generateTestMeeting(meetingRepository);

		Meeting meeting = meetingRepository.findById(testMeeting.getId());
		assertNotNull(meeting);
		assertEquals("Meeting leader id", testMeeting.getLeaderId(), meeting.getLeaderId());
		assertEquals("Meeting title", testMeeting.getTitle(), meeting.getTitle());
		assertEquals("Meeting description", testMeeting.getDescription(), meeting.getDescription());
		assertEquals("Meeting start datetime", testMeeting.getStartDateTime(),
				meeting.getStartDateTime());
		assertEquals("Meeting end datetime", testMeeting.getEndDateTime(),
				meeting.getEndDateTime());
		assertEquals("Meeting location", testMeeting.getLocation(), meeting.getLocation());
		assertEquals("Meeting location type", testMeeting.getLocationType(),
				meeting.getLocationType());
		assertEquals("Meeting participants", testMeeting.getParticipants(),
				meeting.getParticipants());
		assertEquals("Meeting predefined location", testMeeting.getPredefinedLocations(),
				meeting.getPredefinedLocations());
	}

	@Test
	public void findByParticipantPhoneNumber() {
		Meeting testMeeting = TestUtil.generateTestMeeting(meetingRepository);
		testMeeting.addParticipant(TestUtil.generateTestParticipant(participantRepository));
		meetingRepository.save(testMeeting);
		Meeting testMeeting2 = TestUtil.generateTestMeeting(meetingRepository);
		testMeeting2.addParticipant(TestUtil.generateTestParticipant(participantRepository));
		meetingRepository.save(testMeeting2);

		Participant testParticipant = testMeeting.getParticipants().get(0);
		List<Meeting> meetings =
				meetingRepository.findByParticipantPhoneNumber(testParticipant.getPhoneNumber());
		assertFalse("Meetings list empty", meetings.isEmpty());
		for (Meeting meeting : meetings) {
			assertFalse("Meeting participant list empty", meeting.getParticipants().isEmpty());
			boolean foundTestParticipant = false;
			for (Participant participant : meeting.getParticipants()) {
				if (participant.getId() == testParticipant.getId()) {
					foundTestParticipant = true;
					break;
				}
			}
			assertTrue("Found test participant", foundTestParticipant);
		}
	}
}
