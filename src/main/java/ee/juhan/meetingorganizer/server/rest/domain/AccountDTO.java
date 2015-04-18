package ee.juhan.meetingorganizer.server.rest.domain;

public class AccountDTO {

	private String name;
	private String email;
	private String password;
	private String phoneNumber;

	public AccountDTO() {
	}

	public String getName() {
        return name;
    }
	
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
