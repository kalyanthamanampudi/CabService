package cabservice.service;

import java.util.List;
import java.util.Optional;

import cabservice.DTO.Cab;
import cabservice.DTO.Customer;
import cabservice.DTO.User;


public interface Service {
	
	String getuser();

	Optional<User> getUser(String username);

	int addUser(User u);

	List<Cab> getallCabs();

	List<Cab> getallavailablecabs();

	int addCustomer(Customer c);

	int updateCabsCustomer(List<Cab> cabs, String name);

	int unbookupdateCabsCustomer(String name);

	int addcab(Cab cab);

	int deleteUserCustomer(String name);

	String checkCabbooked(String name);

	int deletecab(int cab_id);



}
