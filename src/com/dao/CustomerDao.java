package com.dao;

import java.util.List;

import com.bean.Customer;
import com.exceptions.AccountantException;
import com.exceptions.CustomerException;

public interface CustomerDao {

	public String registerCustomer(String username, String password,String mobile,int balance);
	public String registerCustomer(Customer customer);
	public Customer getCustomerByAcc(String accNo)throws CustomerException;
	public Customer loginCustomer(String username,String password)throws CustomerException;
	public void loginCustomer1(String username,String password)throws CustomerException;
	public void loginAccountant(String username,String password)throws AccountantException;
	public List<Customer> getAllCustomersDetails()throws CustomerException;
	public void updateCustomersDetails(String accNo)throws CustomerException;
	public void removeCustomerAccount(String accNo)throws CustomerException;
	public void depositeMoney(int amount,String accno)throws CustomerException;
	public void withdrawMoney(int amount,String accno)throws CustomerException;
	public void transferMoney(int amount,String accno,String racco)throws CustomerException;
	public void showAllTransactions()throws CustomerException;
	public void checkTransactionHistory(String accno);

	

	
	
	
	
	
	
	
}
