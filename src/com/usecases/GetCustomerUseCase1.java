package com.usecases;

import java.util.Scanner;

import com.bean.Customer;
import com.dao.CustomerDao;
import com.dao.CustomerDaoImpl;
import com.exceptions.CustomerException;

public class GetCustomerUseCase1 {

	public static void main1() {

		Scanner sc = new Scanner(System.in);

		System.out.println("Enter acc number...");
		String acc = sc.nextLine();

		CustomerDao cdao = new CustomerDaoImpl();

		Customer customer;
		try {
			customer = cdao.getCustomerByAcc(acc);

			System.out.println(customer);

		} catch (CustomerException ce) {

			System.out.println(ce.getMessage());

		}

	}

}
