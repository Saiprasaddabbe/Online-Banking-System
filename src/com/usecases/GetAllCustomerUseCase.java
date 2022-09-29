package com.usecases;

import java.util.List;
import java.util.Scanner;

import com.bean.Customer;
import com.dao.CustomerDao;
import com.dao.CustomerDaoImpl;
import com.exceptions.CustomerException;

public class GetAllCustomerUseCase {

	public static void main1() {

		CustomerDao cdao = new CustomerDaoImpl();
		try {
			List<Customer> custormersList = cdao.getAllCustomersDetails();
			custormersList.forEach(c -> System.out.println(c));

		} catch (CustomerException ce) {
			System.out.println(ce.getMessage());

		}

	}



}
