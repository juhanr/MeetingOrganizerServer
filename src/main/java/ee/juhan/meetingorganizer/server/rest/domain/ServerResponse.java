package ee.juhan.meetingorganizer.server.rest.domain;

public class ServerResponse {

	private ServerResult result;
	private String sid;
	private Integer userId;

	public ServerResponse(ServerResult result, String sid, Integer userId) {
		this.result = result;
		this.sid = sid;
		this.userId = userId;
	}

	public ServerResponse(ServerResult result) {
		this.result = result;
	}
	
	public final ServerResult getResult() {
        return result;
    }

	public final void setResult(ServerResult result) {
        this.result = result;
    }

    public final String getSid() {
        return sid;
    }

    public final void setSid(String sid) {
        this.sid = sid;
    }

    public final Integer getUserId() {
        return userId;
    }

    public final void setUserId(Integer userId) {
        this.userId = userId;
    }
}
