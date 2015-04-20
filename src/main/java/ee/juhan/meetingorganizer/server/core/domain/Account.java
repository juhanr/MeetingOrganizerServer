package ee.juhan.meetingorganizer.server.core.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Account implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String hash;

	@Column(nullable = false)
	private String phoneNumber;

	@Column(nullable = false)
	private String sid;

	@OneToMany(fetch = FetchType.LAZY)
	private Set<Meeting> meetings = new HashSet<>();

	protected Account() {
		super();
	}

	public Account(String name, String email, String hash, String phoneNumber,
			String sid) {
		super();
		this.name = name;
		this.email = email;
		this.hash = hash;
		this.phoneNumber = phoneNumber;
		this.sid = sid;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getHash() {
		return hash;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getSid() {
		return sid;
	}

	public Set<Meeting> getMeetings() {
		return meetings;
	}

	public boolean addMeeting(Meeting meeting) {
		return meetings.add(meeting);
	}

}
