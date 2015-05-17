package ee.juhan.meetingorganizer.server.rest;

public final class ControllerConstants {

	// Request paths
	public static final String MEETING_PATH = "/meeting";
	public static final String NEW_PATH = "/new";
	public static final String UPDATE_PARTICIPANT_PATH = "/update-participant";
	public static final String REGISTER_PATH = "/register";
	public static final String LOGIN_PATH = "/login";
	public static final String ACCOUNT_PATH = "/account";
	public static final String CHECK_CONTACTS_PATH = "/check-contacts";
	// Path variables
	public static final String ACCOUNT_ID = "accountId";
	public static final String MEETING_ID = "meetingId";
	public static final String MEETINGS_TYPE = "meetingsType";
	// Path variable values
	public static final String ONGOING_MEETINGS = "ongoing-meetings";
	public static final String FUTURE_MEETINGS = "future-meetings";
	public static final String PAST_MEETINGS = "past-meetings";
	public static final String INVITATIONS = "invitations";
	// Headers
	public static final String CLIENT_TIME_ZONE = "Client-Time-Zone";
	// Cookies
	public static final String SID = "sid";

	private ControllerConstants() {}

}
