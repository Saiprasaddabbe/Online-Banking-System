package com.usecases;

import java.util.Scanner;

import com.bean.Customer;
import com.dao.CustomerDao;
import com.dao.CustomerDaoImpl;
import com.exceptions.CustomerException;

public class LoginUseCase {

	public static void main1() {

		Scanner sc = new Scanner(System.in);

		System.out.println("Enter username...");
		String username = sc.nextLine();

		System.out.println("Enter password...");
		String password = sc.nextLine();

		CustomerDao cdao = new CustomerDaoImpl();
		 try {
			Customer customer = cdao.loginCustomer(username, password);
			System.out.println("log in Successfull\nWelcome, "+customer.getUsername());
		} catch (CustomerException ce) {
			System.out.println(ce.getMessage());
		}

		


	}

}
