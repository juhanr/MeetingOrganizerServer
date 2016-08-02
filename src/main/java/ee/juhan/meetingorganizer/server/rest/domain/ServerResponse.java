package ee.juhan.meetingorganizer.server.rest.domain;

public class ServerResponse {

	private ServerResult result;
	private String sid;
	private AccountDto accountDto;

	public ServerResponse(ServerResult result, String sid, AccountDto accountDto) {
		this.result = result;
		this.sid = sid;
		this.accountDto = accountDto;
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

	public AccountDto getAccountDto() {
		return accountDto;
	}

	public void setAccountDto(AccountDto accountDto) {
		this.accountDto = accountDto;
	}
}
