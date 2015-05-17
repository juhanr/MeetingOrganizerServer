package ee.juhan.meetingorganizer.server.core.util;

import java.io.Serializable;
import java.util.Comparator;

import ee.juhan.meetingorganizer.server.rest.domain.MeetingDTO;

public class MeetingDTOComparator implements Comparator<MeetingDTO>, Serializable {

	@Override
	public final int compare(MeetingDTO o1, MeetingDTO o2) {
		return o1.getStartDateTime().compareTo(o2.getStartDateTime());
	}

}