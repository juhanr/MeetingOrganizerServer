package ee.juhan.meetingorganizer.server.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JsonDateDeserializer extends JsonDeserializer<Date> {

	@Override
	public final Date deserialize(JsonParser jsonparser,
			DeserializationContext deserializationcontext) {
		SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
		try {
			String date = jsonparser.getText();
			return format.parse(date);
		} catch (ParseException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}