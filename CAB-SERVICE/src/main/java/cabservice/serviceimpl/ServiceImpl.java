package cabservice.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import cabservice.DTO.Cab;
import cabservice.DTO.Customer;
import cabservice.DTO.User;
import cabservice.repository.Repository;
import cabservice.service.Service;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service {

	@Autowired
	private Repository repo;

	@Override
	public Optional<User> getUser(String username) {
		Optional<User> u = repo.getUser(username);
		return u;
	}

	@Override
	public int addUser(User u) {
		return repo.addUser(u);
	}

	@Override
	public List<Cab> getallCabs() {
		// TODO Auto-generated method stub
		return repo.getallcabs();
	}

	@Override
	public List<Cab> getallavailablecabs() {
		// TODO Auto-generated method stub
		return repo.getallavailablecabs();
	}

	@Override
	public int addCustomer(Customer c) {

		return repo.addCustomer(c);
	}

	@Override
	public int updateCabsCustomer(List<Cab> cabs, String name) {
		// TODO Auto-generated method stub
		return repo.updateCabsCustomer(cabs, name);
	}

	@Override
	public int unbookupdateCabsCustomer(String name) {
		return repo.unbookupdateCabsCustomer(name);
	}

	@Override
	public int addcab(Cab cab) {
		// TODO Auto-generated method stub
		return repo.addcab(cab);
	}

	@Override
	public int deleteUserCustomer(String name) {
		// TODO Auto-generated method stub
		return repo.deletecustomeruser(name);
	}

	@Override
	public String getuser() {
		return repo.getUser();
	}

	@Override
	public String checkCabbooked(String name) {
		return repo.checkcabbooked(name);
	}

	@Override
	public int deletecab(int cab_id) {
		return repo.deletecab(cab_id);
	}

}
