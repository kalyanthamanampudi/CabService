package cabservice.DTO;

import org.springframework.stereotype.Component;

@Component
public class Customer {

	private int customer_id;
	private String name;
	private long phone;
	private String email;
	private int cab_id;

	public Customer() {
	}

	public Customer( String name, long phone, String email, int cab_id) {
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.cab_id = cab_id;
	}

	public Customer( String name, long phone, String email) {
		this.name = name;
		this.phone = phone;
		this.email = email;
	}
	
	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getCab_id() {
		return cab_id;
	}

	public void setCab_id(int cab_id) {
		this.cab_id = cab_id;
	}

	
}
