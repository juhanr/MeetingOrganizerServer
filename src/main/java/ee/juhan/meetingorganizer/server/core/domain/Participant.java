package ee.juhan.meetingorganizer.server.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import ee.juhan.meetingorganizer.server.rest.domain.ParticipationAnswer;

@Entity
public class Participant implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int accountId;

	@Column(nullable = false)
	private String name;

	private String email;

	@Column(nullable = false)
	private String phoneNumber;

	@Column(nullable = false)
	private ParticipationAnswer participationAnswer = ParticipationAnswer.NOT_ANSWERED;

	private double locationLatitude;

	private double locationLongitude;

	public Participant(int accountId, String name, String email,
			String phoneNumber, ParticipationAnswer participationAnswer,
			double locationLatitude, double locationLongitude) {
		this.accountId = accountId;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.participationAnswer = participationAnswer;
		this.locationLatitude = locationLatitude;
		this.locationLongitude = locationLongitude;
	}

	public Participant(int accountId, String name, String email,
			String phoneNumber) {
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

	public Participant(String name, String email, String phoneNumber) {
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

	public int getId() {
		return id;
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

	public ParticipationAnswer getParticipationAnswer() {
		return participationAnswer;
	}

	public double getLocationLatitude() {
		return locationLatitude;
	}

	public double getLocationLongitude() {
		return locationLongitude;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public void setParticipationAnswer(ParticipationAnswer participationAnswer) {
		this.participationAnswer = participationAnswer;
	}

	public void setLocationLatitude(double locationLatitude) {
		this.locationLatitude = locationLatitude;
	}

	public void setLocationLongitude(double locationLongitude) {
		this.locationLongitude = locationLongitude;
	}

}