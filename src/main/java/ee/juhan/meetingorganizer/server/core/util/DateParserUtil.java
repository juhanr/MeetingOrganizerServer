package ee.juhan.meetingorganizer.server.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateParserUtil {

	private static final SimpleDateFormat URL_DATETIME_FORMAT = new SimpleDateFormat(
			"dd-MM-yyyy-HH-mm");

	public static Date fromClientTimeZone(Date date, TimeZone clientTimeZone) {
		SimpleDateFormat clientTimeZoneFormat = (SimpleDateFormat) getUrlDateTimeFormat()
				.clone();
		clientTimeZoneFormat.setTimeZone(clientTimeZone);
		try {
			return clientTimeZoneFormat.parse(getUrlDateTimeFormat().format(
					date));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Date toClientTimeZone(Date date, TimeZone clientTimeZone) {
		SimpleDateFormat clientTimeZoneFormat = (SimpleDateFormat) getUrlDateTimeFormat()
				.clone();
		clientTimeZoneFormat.setTimeZone(clientTimeZone);
		try {
			return getUrlDateTimeFormat().parse(
					clientTimeZoneFormat.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static SimpleDateFormat getUrlDateTimeFormat() {
		return URL_DATETIME_FORMAT;
	}

}
