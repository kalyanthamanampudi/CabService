package cabservice.repository;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

import cabservice.DTO.Cab;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cabservice.DTO.Customer;
import cabservice.DTO.User;

@Component
public class Repository {

	private User u;

	@Autowired
	private Properties sqlquery;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public String getUser() {
		return u.getName();
	}

	public Optional<User> getUser(String username) {
		String sql = sqlquery.getProperty("get_userByname");
		SqlParameterSource assign = new MapSqlParameterSource("name", username);
		u = namedParameterJdbcTemplate.queryForObject(sql, assign,
				(rs, count) -> new User(rs.getString("name"), rs.getString("password"), rs.getString("roles")));
		return Optional.of(u);
	}

	public int addUser(User u) {
		u.setPassword(passwordEncoder.encode(u.getPassword()));
		String sql = sqlquery.getProperty("add_user");
		SqlParameterSource assign = new MapSqlParameterSource("name", u.getName()).addValue("password", u.getPassword())
				.addValue("roles", u.getRoles());
		return namedParameterJdbcTemplate.update(sql, assign);
	}

	public List<Cab> getallcabs() {
		String sql = sqlquery.getProperty("get_allcabs");
		List<Cab> cabs = namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource(),
				(rs, c) -> new Cab(rs.getInt("cab_id"), rs.getString("driver"), rs.getString("availability")));
		return cabs;
	}

	public List<Cab> getallavailablecabs() {
		String sql = sqlquery.getProperty("get_allavailablecabs");
		List<Cab> cabs = namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource(),
				(rs, c) -> new Cab(rs.getInt("cab_id"), rs.getString("driver"), rs.getString("availability")));
		return cabs;
	}

	public int addCustomer(Customer c) {
		String sql = sqlquery.getProperty("add_customer");
		SqlParameterSource assign = new MapSqlParameterSource("name", c.getName()).addValue("phone", c.getPhone())
				.addValue("email", c.getEmail());
		return namedParameterJdbcTemplate.update(sql, assign);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public int updateCabsCustomer(List<Cab> cabslist, String name) {
		TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
		int c = 0;
		try {
			Cab ca;
			ca = cabslist.get(1);
			String sql = sqlquery.getProperty("update_cabs");
			String sqlCustomer = sqlquery.getProperty("update_Customer");
			SqlParameterSource assign = new MapSqlParameterSource("cab_id", ca.getCab_id());
			SqlParameterSource assignname = new MapSqlParameterSource("cab_id", ca.getCab_id()).addValue("name", name);
			namedParameterJdbcTemplate.update(sql, assign);
			c = namedParameterJdbcTemplate.update(sqlCustomer, assignname);
		} catch (Exception ex) {
			status.setRollbackOnly();
		}
		return c;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public int unbookupdateCabsCustomer(String name) {
		TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
		int x = 0;
		try {
			SqlParameterSource assign = new MapSqlParameterSource("name", name).addValue("null", null);
			String sql1 = sqlquery.getProperty("update_customerunbook");
			String sql2 = sqlquery.getProperty("update_cabsunbook");
			namedParameterJdbcTemplate.update(sql2, assign);
			return namedParameterJdbcTemplate.update(sql1, assign);
		} catch (Exception ex) {
			status.setRollbackOnly();
		}
		return x;
	}

	public int addcab(Cab cab) {
		String sql = sqlquery.getProperty("add_cab");
		SqlParameterSource assign = new MapSqlParameterSource("driver", cab.getDriver()).addValue("availability",
				cab.getAvailability());
		return namedParameterJdbcTemplate.update(sql, assign);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public int deletecustomeruser(String name) {
		TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
		int x = 0;
		try {
			SqlParameterSource assign = new MapSqlParameterSource("name", name);
			String sql1 = sqlquery.getProperty("delete_customer");
			String sql2 = sqlquery.getProperty("delete_user");
			namedParameterJdbcTemplate.update(sql2, assign);
			return namedParameterJdbcTemplate.update(sql1, assign);
		} catch (Exception ex) {
			status.setRollbackOnly();
		}
		return x;
	}

	public String checkcabbooked(String name) {
		SqlParameterSource assign = new MapSqlParameterSource("name", name);
		String sql = sqlquery.getProperty("check_cabbooked");
		return namedParameterJdbcTemplate.queryForObject(sql, assign, String.class);
		 
		
	}

	public int deletecab(int cab_id) {
		String sql = sqlquery.getProperty("delete_cab");
		SqlParameterSource assign = new MapSqlParameterSource("cab_id",cab_id);
		return namedParameterJdbcTemplate.update(sql, assign);
	}

}
