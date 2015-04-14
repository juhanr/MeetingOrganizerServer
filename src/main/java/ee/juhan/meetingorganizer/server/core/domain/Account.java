package ee.juhan.meetingorganizer.server.core.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Account implements Serializable {

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

	protected Account() {
		super();
	}

	public Account(String email, String hash, String sid) {
		super();
		this.email = email;
		this.hash = hash;
		this.sid = sid;
	}

	public String getEmail() {
		return email;
	}

	public String getHash() {
		return hash;
	}

	public int getId() {
		return id;
	}

	public String getSid() {
		return sid;
	}

}
