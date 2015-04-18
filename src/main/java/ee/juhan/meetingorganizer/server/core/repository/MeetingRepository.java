package ee.juhan.meetingorganizer.server.core.repository;

import org.springframework.data.repository.CrudRepository;

import ee.juhan.meetingorganizer.server.core.domain.Meeting;

public interface MeetingRepository extends CrudRepository<Meeting, Integer> {

	Meeting findById(int id);

}