package com.dao;

import com.bean.Customer;

public interface CustomerDao {

	public String registerCustomer(String username, String password,String mobile);
	public String registerCustomer(Customer customer);
	
	
	
	
	
	
	
}
