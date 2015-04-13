package ee.juhan.meetingorganizer.server.core.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String hash;

	@Column(nullable = false)
	private String sid;

	protected User() {
		super();
	}

	public User(String email, String hash, String sid) {
		super();
		this.email = email;
		this.hash = hash;
		this.sid = sid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHash() {
		return hash;
	}

	public void setPassword(String password) {
		this.hash = password;
	}

	public int getId() {
		return id;
	}

	public String getSid() {
		return sid;
	}

}