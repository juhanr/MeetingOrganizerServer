package ee.juhan.meetingorganizer.server.core.repository;

import org.springframework.data.repository.CrudRepository;

import ee.juhan.meetingorganizer.server.core.domain.User;

public interface UserRepository extends CrudRepository<User, Integer> {

	User findByEmail(String email);

	User findBySid(String sid);

}