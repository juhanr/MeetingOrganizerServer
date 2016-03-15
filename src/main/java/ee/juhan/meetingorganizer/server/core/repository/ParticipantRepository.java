package ee.juhan.meetingorganizer.server.core.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

import ee.juhan.meetingorganizer.server.core.domain.Meeting;
import ee.juhan.meetingorganizer.server.core.domain.Participant;
import ee.juhan.meetingorganizer.server.rest.domain.ParticipationAnswer;

public interface ParticipantRepository extends CrudRepository<Participant, Integer> {

	Participant findById(int id);

	List<Participant> findByPhoneNumber(String phoneNumber);

	@Query("select m from Participant p " +
			"join p.meeting m " +
			"where p.account.id = ?1 " +
			"and m.endDateTime >= now() " +
			"and p.participationAnswer = ?2 " +
			"order by m.startDateTime")
	List<Meeting> findActiveMeetings(int accountId, ParticipationAnswer participationAnswer);

	@Query("select m from Participant p " +
			"join p.meeting m " +
			"where p.account.id = ?1 " +
			"and m.endDateTime < now() " +
			"and p.participationAnswer = ?2 " +
			"order by m.startDateTime desc")
	List<Meeting> findPastMeetings(int accountId, ParticipationAnswer participationAnswer);

	@Query("select m from Participant p " +
			"join p.meeting m " +
			"where p.account.id = ?1 " +
			"and m.endDateTime > now() " +
			"and p.participationAnswer = ?2 " +
			"order by m.startDateTime")
	List<Meeting> findInvitations(int accountId, ParticipationAnswer participationAnswer);

	@Query("select p from Participant p " +
			"where p.meeting.id = ?1 " +
			"order by p.id")
	List<Participant> findParticipantsByMeetingId(int meetingId);

	@Query("select m from Participant p " +
			"join p.meeting m " +
			"where p.phoneNumber = ?1 " +
			"order by m.id")
	List<Meeting> findMeetingByPhoneNumber(String phoneNumber);

}
