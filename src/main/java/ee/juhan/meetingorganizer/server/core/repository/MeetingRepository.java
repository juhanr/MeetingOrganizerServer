package ee.juhan.meetingorganizer.server.core.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

import ee.juhan.meetingorganizer.server.core.domain.Meeting;

public interface MeetingRepository extends CrudRepository<Meeting, Integer> {

	Meeting findById(int id);

	@Query("select m from Meeting m join m.participants p where p.phoneNumber = ?1")
	List<Meeting> findByParticipantPhoneNumber(String phoneNumber);

}