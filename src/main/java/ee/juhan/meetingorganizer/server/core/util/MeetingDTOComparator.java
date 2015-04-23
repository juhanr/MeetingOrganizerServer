package ee.juhan.meetingorganizer.server.core.util;

import java.util.Comparator;

import ee.juhan.meetingorganizer.server.rest.domain.MeetingDTO;

public class MeetingDTOComparator implements Comparator<MeetingDTO> {

	@Override
	public int compare(MeetingDTO o1, MeetingDTO o2) {
		return o1.getStartDateTime().compareTo(o2.getStartDateTime());
	}

}