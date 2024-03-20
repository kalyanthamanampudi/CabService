package cabservice.DTO;

import org.springframework.stereotype.Component;

@Component
public class Cab {

	private int cab_id;
	private String driver;
	private String availability;

	public Cab() {
	}

	public Cab(int cab_id, String driver, String availability) {
		this.cab_id = cab_id;
		this.driver = driver;
		this.availability = availability;
	}

	public int getCab_id() {
		return cab_id;
	}

	public void setCab_id(int cab_id) {
		this.cab_id = cab_id;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

}
