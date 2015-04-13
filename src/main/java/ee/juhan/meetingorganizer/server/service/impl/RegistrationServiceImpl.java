package ee.juhan.meetingorganizer.server.service.impl;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.juhan.meetingorganizer.server.core.domain.User;
import ee.juhan.meetingorganizer.server.core.repository.UserRepository;
import ee.juhan.meetingorganizer.server.core.util.HasherUtil;
import ee.juhan.meetingorganizer.server.core.util.SIDGeneratorUtil;
import ee.juhan.meetingorganizer.server.rest.domain.ServerResponse;
import ee.juhan.meetingorganizer.server.rest.domain.ServerResult;
import ee.juhan.meetingorganizer.server.service.RegistrationService;

@Service
public class RegistrationServiceImpl implements RegistrationService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public ServerResponse registrationRequest(String email, String password) {
		if (checkEmail(email) == ServerResult.EMAIL_IN_USE)
			return new ServerResponse(ServerResult.EMAIL_IN_USE);
		User user = createUser(email, password);
		return new ServerResponse(ServerResult.SUCCESS, user.getSid(),
				user.getId());
	}

	private ServerResult checkEmail(String email) {
		User user = userRepository.findByEmail(email);
		if (user == null)
			return ServerResult.SUCCESS;
		else
			return ServerResult.EMAIL_IN_USE;
	}

	private User createUser(String email, String password) {
		try {
			String sid = SIDGeneratorUtil.generateSID();
			User user = new User(email, HasherUtil.createHash(password), sid);
			userRepository.save(user);
			return user;
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
			return null;
		}
	}

}
