package cabservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cabservice.DTO.AuthRequest;
import cabservice.DTO.Cab;
import cabservice.DTO.Customer;
import cabservice.DTO.User;
import cabservice.DTO.UserCustomer;
import cabservice.service.Service;
import cabservice.serviceimpl.JwtServiceImpl;

@RestController
@RequestMapping("/cabservice")
public class Controller extends Thread {

	@Autowired
	private Service service;

	@Autowired
	private JwtServiceImpl jwtservice;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/signin")
	public ResponseEntity<?> addUser(@RequestBody UserCustomer uc) {
		try {
			User u = new User(uc.getEmail(), uc.getPassword(), uc.getRoles());
			if (uc.getRoles().equalsIgnoreCase("customer")) {
				Customer c = new Customer(uc.getName(), uc.getPhone(), uc.getEmail());
				int m = service.addCustomer(c);
				int n = service.addUser(u);
				if (n > 0 && m > 0) {
					return new ResponseEntity<>("Customer account created successfully", HttpStatus.OK);
				} else
					throw new Exception();
			} else {
				int n = service.addUser(u);
				if (n > 0) {
					return new ResponseEntity<>("new admin created successfully", HttpStatus.OK);
				} else
					throw new Exception();
			}
		} catch (Exception ex) {
			return new ResponseEntity<>("server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/allcabs")
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<?> getallcabs() {
		try {
			return new ResponseEntity<>(service.getallCabs(), HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>("server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/availablecabs")
	@PreAuthorize("hasAuthority('customer')")
	public ResponseEntity<?> getallavailablecabs() {
		try {
			return new ResponseEntity<>(service.getallavailablecabs(), HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>("server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/bookcab")
	@PreAuthorize("hasAuthority('customer')")
	public ResponseEntity<?> bookCabs() {
		try {
			List<Cab> cabs = service.getallavailablecabs();
			if (!cabs.isEmpty()) {
				int n = service.updateCabsCustomer(cabs, service.getuser());
				if (n > 0) {
					return new ResponseEntity<>("cab booked successfully", HttpStatus.OK);
				} else
					throw new Exception();

			} else {
				return new ResponseEntity<>("No cabs available right now", HttpStatus.NOT_ACCEPTABLE);
			}

		} catch (Exception ex) {
			return new ResponseEntity<>("server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/unbookcab/{name}")
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<?> unbookCabs(@PathVariable("name") String name) {
		try {
			if (service.checkCabbooked(name) != null) {
				int n = service.unbookupdateCabsCustomer(name);
				if (n > 0)
					return new ResponseEntity<>("cab unbooked successfully", HttpStatus.OK);
				else
					throw new Exception();
			} else
				return new ResponseEntity<>("cab not booked yat", HttpStatus.NOT_ACCEPTABLE);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>("server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/addcab")
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<?> addcab(@RequestBody Cab cab) {
		try {
			int n = service.addcab(cab);
			if (n > 0) {
				return new ResponseEntity<>("cab added successfully", HttpStatus.OK);
			} else
				throw new Exception();
		} catch (Exception ex) {
			return new ResponseEntity<>("server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping("/deletecab/{cab_id}")
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<?> addcab(@PathVariable("cab_id") int cab_id) {
		try {
			int n = service.deletecab(cab_id);
			if (n > 0) {
				return new ResponseEntity<>("cab added successfully", HttpStatus.OK);
			} else
				throw new Exception();
		} catch (Exception ex) {
			return new ResponseEntity<>("server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping("/signoff/{name}")
	@PreAuthorize("hasAuthority('customer')")
	public ResponseEntity<?> deleteUser(@PathVariable("name") String name) {
		try {
			int m = service.deleteUserCustomer(name);
			if (m > 0) {
				return new ResponseEntity<>("Customer account deleted successfully", HttpStatus.OK);
			} else
				throw new Exception();
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>("server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/authenticate")
	public String authenticateAndgenerateToken(@RequestBody AuthRequest au) {
		Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(au.getName(), au.getPassword()));
		if (auth.isAuthenticated()) {
			return jwtservice.generateToken(au.getName());
		} else {
			throw new UsernameNotFoundException("invalid user request");
		}
	}

}
