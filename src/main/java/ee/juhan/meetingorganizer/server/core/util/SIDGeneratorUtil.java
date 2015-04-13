package ee.juhan.meetingorganizer.server.core.util;

import java.math.BigInteger;
import java.security.SecureRandom;

public class SIDGeneratorUtil {

	public static String generateSID() {
		return new BigInteger(130, new SecureRandom()).toString(32);
	}
}
