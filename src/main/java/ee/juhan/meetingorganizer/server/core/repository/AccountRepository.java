package ee.juhan.meetingorganizer.server.core.repository;

import org.springframework.data.repository.CrudRepository;

import ee.juhan.meetingorganizer.server.core.domain.Account;

public interface AccountRepository extends CrudRepository<Account, Integer> {

	Account findByEmail(String email);

	Account findBySid(String sid);

}