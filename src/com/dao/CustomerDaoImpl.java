package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

import com.bean.Customer;
import com.utility.DBUtil;

public class CustomerDaoImpl implements CustomerDao {

	@Override
	public String registerCustomer(Customer customer) {
		String message = "Not inserted..";

		try (Connection conn = DBUtil.provideConnection()) {

			PreparedStatement ps = conn.prepareStatement("insert into customers values(?,?,?,?)");
			ps.setString(1, customer.getAccNo());
			ps.setString(2, customer.getUsername());
			ps.setString(3, customer.getPassword());
			ps.setString(4, customer.getMobile());

			int x = ps.executeUpdate();
			if (x > 0) {
				message = "Customer Registered Successfully..!";
			}

		} catch (SQLException e) {
			message = e.getMessage();
		}

		return message;

	}

	@Override
	public String registerCustomer(String username, String password, String mobile) {
		// Generating 12 digit account number
		Random rnd = new Random();
		int number = rnd.nextInt(999999999);
		String accNo = String.format("%9d", number);
		accNo = accNo + "000";
		
		String message = "Not inserted..";

		try (Connection conn = DBUtil.provideConnection()) {

			PreparedStatement ps = conn.prepareStatement("insert into customers values(?,?,?,?)");
			ps.setString(1, accNo);
			ps.setString(2, username);
			ps.setString(3,password);
			ps.setString(4, mobile);

			int x = ps.executeUpdate();
			if (x > 0) {
				message = "Customer Registered Successfully..!";
			}

		} catch (SQLException e) {
			message = e.getMessage();
		}

		return message;

	}

}
