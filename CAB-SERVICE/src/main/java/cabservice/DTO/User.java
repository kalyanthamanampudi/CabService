package cabservice.DTO;

import org.springframework.stereotype.Component;

@Component
public class User {

	private String name;
	private String password;
	private String roles;

	public User() {

	}

	public User(String name, String password, String roles) {

		this.name = name;
		this.password = password;
		this.roles = roles;
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

	@Override
	public String toString() {
		return "User [name=" + name + ", password=" + password + ", roles=" + roles + "]";
	}

}
