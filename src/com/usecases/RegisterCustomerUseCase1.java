package com.usecases;

import java.util.Scanner;

import com.dao.CustomerDao;
import com.dao.CustomerDaoImpl;

public class RegisterCustomerUseCase1 {
	
	public static void main1() {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter username...");
		String username = sc.nextLine();

		System.out.println("Enter Mobile Number...");
		String mobile = sc.nextLine();

		System.out.println("Enter Password...");
		String password = sc.nextLine();
		
		System.out.println("Enter minimum balance to open account 10rs...");
		int Balance = sc.nextInt();
		
		CustomerDao cdao = new CustomerDaoImpl();
		String message = cdao.registerCustomer(username, password, mobile,Balance);
		
		System.out.println(message);
		
		
		
		
	}

}
