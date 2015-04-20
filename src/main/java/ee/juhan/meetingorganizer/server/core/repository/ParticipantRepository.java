package ee.juhan.meetingorganizer.server.core.repository;

import org.springframework.data.repository.CrudRepository;

import ee.juhan.meetingorganizer.server.core.domain.Participant;

public interface ParticipantRepository extends
		CrudRepository<Participant, Integer> {

	Participant findById(int id);

}
