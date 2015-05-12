package ee.juhan.meetingorganizer.server.rest.domain;

public class ContactDTO {

	private int accountId;
	private String name;
	private String email;
	private String phoneNumber;

	public ContactDTO() {

	}

	public final int getAccountId() {
        return accountId;
    }

    public final void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public final String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final String getEmail() {
        return email;
    }

    public final void setEmail(String email) {
        this.email = email;
    }

    public final String getPhoneNumber() {
        return phoneNumber;
    }

    public final void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
