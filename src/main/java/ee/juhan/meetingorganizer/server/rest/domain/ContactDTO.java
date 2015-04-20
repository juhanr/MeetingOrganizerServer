package ee.juhan.meetingorganizer.server.rest.domain;

public class ContactDTO {

	private int accountId;
	private String name;
	private String email;
	private String phoneNumber;

	public ContactDTO() {

	}

	public int getAccountId() {
		return accountId;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
