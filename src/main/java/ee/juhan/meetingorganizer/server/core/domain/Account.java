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
import javax.persistence.ManyToMany;

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

	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Meeting> meetings = new HashSet<>();

	protected Account() {}

	public Account(String name, String email, String hash, String phoneNumber, String sid) {
		this.name = name;
		this.email = email;
		this.hash = hash;
		this.phoneNumber = phoneNumber;
		this.sid = sid;
	}

	public final int getId() {
		return id;
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

	public final String getHash() {
		return hash;
	}

	public final void setHash(String hash) {
		this.hash = hash;
	}

	public final String getPhoneNumber() {
		return phoneNumber;
	}

	public final void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public final String getSid() {
		return sid;
	}

	public final void setSid(String sid) {
		this.sid = sid;
	}

	public final Set<Meeting> getMeetings() {
		return meetings;
	}

	public final void setMeetings(Set<Meeting> meetings) {
		this.meetings = meetings;
	}

	public final boolean addMeeting(Meeting meeting) {
		return meetings.add(meeting);
	}



}
