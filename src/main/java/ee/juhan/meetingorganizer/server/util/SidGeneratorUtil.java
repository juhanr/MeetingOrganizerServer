package ee.juhan.meetingorganizer.server.util;

import java.math.BigInteger;
import java.security.SecureRandom;

public final class SidGeneratorUtil {

	private static final int SID_MAX_BIT_LENGTH = 130;
	private static final int RANDOM_ENCODING_BASE = 32;

	private SidGeneratorUtil() {}

	public static String generateSid() {
		return new BigInteger(SID_MAX_BIT_LENGTH, new SecureRandom())
				.toString(RANDOM_ENCODING_BASE);
	}
}
