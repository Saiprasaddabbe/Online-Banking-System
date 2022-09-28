package com.dao;

import java.util.List;

import com.bean.Customer;
import com.exceptions.CustomerException;

public interface CustomerDao {

	public String registerCustomer(String username, String password,String mobile);
	public String registerCustomer(Customer customer);
	public Customer getCustomerByAcc(String accNo)throws CustomerException;
	public Customer loginCustomer(String username,String password)throws CustomerException;
	public List<Customer> getAllCustomersDetails()throws CustomerException;

	
	
	
	
	
	
	
}
