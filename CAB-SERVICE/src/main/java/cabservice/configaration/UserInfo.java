package cabservice.configaration;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import cabservice.DTO.User;
import cabservice.service.Service;

@Component
public class UserInfo implements UserDetailsService {

	@Autowired
	private Service service;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = service.getUser(username);
		return user.map(UserdetailInfo::new)
				.orElseThrow(() -> new UsernameNotFoundException("no user found with the given name"));

	}

}
