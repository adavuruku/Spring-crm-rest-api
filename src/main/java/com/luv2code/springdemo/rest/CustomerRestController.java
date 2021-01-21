package com.luv2code.springdemo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.service.CustomerService;

@RestController
@RequestMapping("/api")
public class CustomerRestController {

	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/customers")
	public List<Customer> getCustomers(){
		return customerService.getCustomers();
	}
	
	@GetMapping("/customers/{customerId}")
	public Customer getCustomer(@PathVariable("customerId") int customerId){
		// check the customerId exist or throw error
		Customer customer = customerService.getCustomer(customerId);
		if ( customer == null ) {			
			throw new StudentNotFoundException("Customer Id not found - " + customerId);
		}
		return customer;
	}
	
	@PostMapping("/customers")
	public Customer addCustomer(@RequestBody Customer theCustomer){
		theCustomer.setId(0); 
		//we are setting Id to zero because we are using Hibernate (saveOrUpdate) in our service
		// if we use save method there will be no need to set it to zero
		customerService.saveCustomer(theCustomer);
		return theCustomer;
	}
	
	@PutMapping("/customers")
	public Customer updateCustomer(@RequestBody Customer theCustomer){
		//checkk to make sure customer exist
		Customer customer = customerService.getCustomer(theCustomer.getId());
		if ( customer == null ) {			
			throw new StudentNotFoundException("Customer Id not found - " + theCustomer.getId());
		}
		//update the customer
		customerService.saveCustomer(theCustomer);
		return theCustomer;
	}
	
	@DeleteMapping("/customers/{customerId}")
	public String deleteCustomer(@PathVariable("customerId") int customerId){
		//checkk to make sure customer exist
		Customer customer = customerService.getCustomer(customerId);
		if ( customer == null ) {			
			throw new StudentNotFoundException("Customer Id not found - " + customerId);
		}
		//Delete the customer
		customerService.deleteCustomer(customerId);
		return "Deleted Customer " + customerId;
	}
}
