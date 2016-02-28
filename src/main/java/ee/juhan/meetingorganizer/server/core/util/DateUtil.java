package ee.juhan.meetingorganizer.server.core.util;

import java.text.SimpleDateFormat;

public final class DateUtil {

	private static final SimpleDateFormat URL_DATETIME_FORMAT =
			new SimpleDateFormat("dd-MM-yyyy-HH-mm");

	private DateUtil() {}

	public static SimpleDateFormat getUrlDateTimeFormat() {
		return URL_DATETIME_FORMAT;
	}

}
