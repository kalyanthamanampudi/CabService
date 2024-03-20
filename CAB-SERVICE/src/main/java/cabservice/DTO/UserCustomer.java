package cabservice.DTO;

public class UserCustomer {

	private String name;
	private String password;
	private String roles;
	private Long phone;
	private String email;
	
	
	
	public UserCustomer() {
	}



	public UserCustomer(String name, String password, String roles, Long phone, String email) {
		super();
		this.name = name;
		this.password = password;
		this.roles = roles;
		this.phone = phone;
		this.email = email;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getRoles() {
		return roles;
	}



	public void setRoles(String roles) {
		this.roles = roles;
	}



	public Long getPhone() {
		return phone;
	}



	public void setPhone(Long phone) {
		this.phone = phone;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}

}
