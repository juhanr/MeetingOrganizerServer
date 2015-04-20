package ee.juhan.meetingorganizer.server.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ee.juhan.meetingorganizer.server.core.domain.Account;
import ee.juhan.meetingorganizer.server.core.domain.Meeting;

public interface AccountRepository extends CrudRepository<Account, Integer> {

	Account findById(int id);

	Account findByEmail(String email);

	Account findBySid(String sid);

	Account findByPhoneNumber(String phoneNumber);

	@Query("select meetings from Account as a where (a.id = ?1)")
	List<Meeting> findMeetingsById(int id);

}