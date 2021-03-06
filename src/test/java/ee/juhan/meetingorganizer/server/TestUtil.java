package ee.juhan.meetingorganizer.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ee.juhan.meetingorganizer.server.core.domain.Account;
import ee.juhan.meetingorganizer.server.core.domain.Meeting;
import ee.juhan.meetingorganizer.server.core.domain.Participant;
import ee.juhan.meetingorganizer.server.core.repository.AccountRepository;
import ee.juhan.meetingorganizer.server.core.repository.MeetingRepository;
import ee.juhan.meetingorganizer.server.core.repository.ParticipantRepository;
import ee.juhan.meetingorganizer.server.rest.domain.ContactDto;
import ee.juhan.meetingorganizer.server.rest.domain.LocationChoice;
import ee.juhan.meetingorganizer.server.rest.domain.MapLocation;
import ee.juhan.meetingorganizer.server.rest.domain.MeetingStatus;
import ee.juhan.meetingorganizer.server.rest.domain.ParticipationAnswer;
import ee.juhan.meetingorganizer.server.rest.domain.SendGpsLocationAnswer;
import ee.juhan.meetingorganizer.server.util.HasherUtil;
import ee.juhan.meetingorganizer.server.util.SidGeneratorUtil;

@SuppressWarnings("unused")
public final class TestUtil {

	public static final String TEST_ACCOUNT_PASSWORD = "test";
	private static final String TEST_USER_NAME = "Test User";
	private static final String TEST_EMAIL = "test.user@test.com";
	private static final String TEST_PHONE_NUMBER = "+100 00000000";
	private static final String TEST_MEETING_TITLE = "Test Meeting";
	private static final String TEST_MEETING_DESCRIPTION = "Test Description";
	private static final MapLocation TEST_MAP_LOCATION = new MapLocation(100.0, 100.0);
	private static final String TEST_LOCATION_NAME = "Test location";
	private static final Date TEST_DATE = new Date();

	private static final Logger LOG = LoggerFactory.getLogger(TestUtil.class);

	private TestUtil() {}

	public static ContactDto generateTestContact() {
		return generateTestContact(0);
	}

	public static ContactDto generateTestContact(int id) {
		return new ContactDto(id, "Test Contact " + id, "test.contact" + id + "@test.com",
				"+10" + id + " " + id * 8);
	}

	public static List<ContactDto> generateTestContactsList(int size) {
		List<ContactDto> contactsList = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			contactsList.add(generateTestContact(i));
		}
		return contactsList;
	}

	public static Account generateTestAccount(AccountRepository accountRepository) {
		try {
			Account account = new Account(TEST_USER_NAME, TEST_EMAIL,
					HasherUtil.createHash(TEST_ACCOUNT_PASSWORD), TEST_PHONE_NUMBER,
					SidGeneratorUtil.generateSid());
			accountRepository.save(account);
			account.setName(account.getName() + account.getId());
			account.setEmail(account.getEmail() + account.getId());
			account.setPhoneNumber(account.getPhoneNumber() + account.getId());
			accountRepository.save(account);
			return account;
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Meeting generateTestMeeting(MeetingRepository meetingRepository, int leaderId) {
		Meeting meeting =
				new Meeting(leaderId, TEST_MEETING_TITLE, TEST_MEETING_DESCRIPTION, TEST_DATE,
						TEST_DATE, TEST_MAP_LOCATION, LocationChoice.SPECIFIC_LOCATION,
						MeetingStatus.ACTIVE);
		meetingRepository.save(meeting);
		meeting.setTitle(meeting.getTitle() + meeting.getId());
		meeting.setDescription(meeting.getDescription() + meeting.getId());
		meeting.setMapLocation(generateTestMapLocation(meeting.getId()));
		meetingRepository.save(meeting);
		return meeting;
	}

	public static Participant generateTestParticipant(ParticipantRepository participantRepository,
			Account account, Meeting meeting) {
		Participant participant =
				new Participant(account, meeting, TEST_USER_NAME, TEST_EMAIL, TEST_PHONE_NUMBER,
						ParticipationAnswer.PARTICIPATING, SendGpsLocationAnswer.NO_ANSWER,
						TEST_MAP_LOCATION, TEST_DATE);
		participantRepository.save(participant);
		participant.setName(participant.getName() + participant.getId());
		participant.setEmail(participant.getEmail() + participant.getId());
		participant.setPhoneNumber(participant.getPhoneNumber() + participant.getId());
		participant.setMapLocation(generateTestMapLocation(participant.getId()));
		participantRepository.save(participant);
		return participant;
	}

	private static MapLocation generateTestMapLocation(double coordinate) {
		return new MapLocation(coordinate, -coordinate);
	}

}
