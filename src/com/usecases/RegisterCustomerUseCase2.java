package com.usecases;

import java.util.Scanner;

import com.bean.Customer;
import com.dao.CustomerDao;
import com.dao.CustomerDaoImpl;

public class RegisterCustomerUseCase2 {

	public static void main1() {

		Scanner sc = new Scanner(System.in);

		System.out.println("Enter username...");
		String username = sc.next();

		System.out.println("Enter Mobile Number...");
		String mobile = sc.next();

		System.out.println("Enter Password...");
		String password = sc.next();

		System.out.println("Enter Minimum balance to open account 10rs...");
		int balance = sc.nextInt();

		Customer customer = new Customer();

		customer.setUsername(username);
		customer.setMobile(mobile);
		customer.setPassword(password);
        customer.setBalance(balance);
        
		CustomerDao cdao = new CustomerDaoImpl();

		String message = cdao.registerCustomer(customer);

		System.out.println(message);
		System.out.println("Your account no is :"+customer.getAccNo());

	}

}
