package ee.juhan.meetingorganizer.server.rest.domain;

public class ServerResponse {

	private ServerResult result;
	private String sid;
	private AccountDto account;

	public ServerResponse(ServerResult result, String sid, AccountDto account) {
		this.result = result;
		this.sid = sid;
		this.account = account;
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

	public AccountDto getAccount() {
		return account;
	}

	public void setAccount(AccountDto account) {
		this.account = account;
	}
}
