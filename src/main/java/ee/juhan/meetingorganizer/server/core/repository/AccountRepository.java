package ee.juhan.meetingorganizer.server.core.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

import ee.juhan.meetingorganizer.server.core.domain.Account;
import ee.juhan.meetingorganizer.server.core.domain.Meeting;
import ee.juhan.meetingorganizer.server.rest.domain.ParticipationAnswer;

public interface AccountRepository extends CrudRepository<Account, Integer> {

	Account findById(int id);

	Account findByEmail(String email);

	Account findBySid(String sid);

	Account findByPhoneNumber(String phoneNumber);

	@Query("select m from Account a where (a.id = ?1)")
	List<Meeting> findMeetingsById(int id);

	@Query("select m from Account a join a.meetings m join m.participants p " +
			"where a.id = ?1 and now() between m.startDateTime and m.endDateTime " +
			"and p.accountId = a.id and p.participationAnswer = ?2 " +
			"order by m.startDateTime")
	List<Meeting> findCurrentMeetings(int id, ParticipationAnswer participationAnswer);

	@Query("select m from Account a join a.meetings m join m.participants p " +
			"where a.id = ?1 and m.startDateTime > now() " +
			"and p.accountId = a.id and p.participationAnswer = ?2 " +
			"order by m.startDateTime")
	List<Meeting> findFutureMeetings(int id, ParticipationAnswer participationAnswer);

	@Query("select m from Account a join a.meetings m join m.participants p " +
			"where a.id = ?1 and m.endDateTime < now() " +
			"and p.accountId = a.id and p.participationAnswer = ?2 " +
			"order by m.startDateTime")
	List<Meeting> findPastMeetings(int id, ParticipationAnswer participationAnswer);

	@Query("select m from Account a join a.meetings m join m.participants p " +
			"where a.id = ?1 and m.endDateTime > now() " +
			"and p.accountId = a.id and p.participationAnswer = ?2 " +
			"order by m.startDateTime")
	List<Meeting> findInvitations(int id, ParticipationAnswer participationAnswer);


}