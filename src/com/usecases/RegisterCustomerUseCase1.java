package com.usecases;

import java.util.Scanner;

import com.dao.CustomersDao;
import com.dao.CustomersDaoImpl;

public class RegisterCustomerUseCase1 {
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter username...");
		String username = sc.nextLine();

		System.out.println("Enter Mobile Number...");
		String mobile = sc.nextLine();

		System.out.println("Enter Password...");
		String password = sc.nextLine();
		
		CustomersDao cdao = new CustomersDaoImpl();
		String message = cdao.registerCustomer(username, password, mobile);
		
		System.out.println(message);
		
		
		
		
	}

}
