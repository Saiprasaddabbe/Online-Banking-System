package com.usecases;

import java.util.Scanner;

import com.bean.Customer;
import com.dao.CustomerDao;
import com.dao.CustomerDaoImpl;

public class RegisterCustomerUseCase2 {

	public static void main1() {

		Scanner sc = new Scanner(System.in);

		System.out.println("Enter username...");
		String username = sc.nextLine();

		System.out.println("Enter Mobile Number...");
		String mobile = sc.nextLine();

		System.out.println("Enter Password...");
		String password = sc.nextLine();

		Customer customer = new Customer();

		customer.setUsername(username);
		customer.setMobile(mobile);
		customer.setPassword(password);

		CustomerDao cdao = new CustomerDaoImpl();

		String message = cdao.registerCustomer(customer);

		System.out.println(message);

	}

}
