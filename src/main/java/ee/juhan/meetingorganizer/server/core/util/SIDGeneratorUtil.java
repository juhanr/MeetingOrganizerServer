package ee.juhan.meetingorganizer.server.core.util;

import java.math.BigInteger;
import java.security.SecureRandom;

public final class SIDGeneratorUtil {

	private static final int SID_MAX_BIT_LENGTH = 130;
	private static final int RANDOM_ENCODING_BASE = 32;

	private SIDGeneratorUtil() {}

	public static String generateSID() {
		return new BigInteger(SID_MAX_BIT_LENGTH, new SecureRandom())
				.toString(RANDOM_ENCODING_BASE);
	}
}
