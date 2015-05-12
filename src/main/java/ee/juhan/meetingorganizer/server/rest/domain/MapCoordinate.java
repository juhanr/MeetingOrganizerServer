package ee.juhan.meetingorganizer.server.rest.domain;

import java.io.Serializable;

public class MapCoordinate implements Serializable {

	private static final long serialVersionUID = 1L;

	private Double latitude;
	private Double longitude;

	public MapCoordinate() {

	}

	public MapCoordinate(Double latitude, Double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public final Double getLatitude() {
        return latitude;
    }

    public final void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public final Double getLongitude() {
        return longitude;
    }

    public final void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

}
