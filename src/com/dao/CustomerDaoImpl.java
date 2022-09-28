package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.bean.Customer;
import com.exceptions.CustomerException;
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
			ps.setString(3, password);
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

	@Override
	public Customer getCustomerByAcc(String accNo) throws CustomerException {
		Customer customer = null;

		try (Connection conn = DBUtil.provideConnection()) {

			PreparedStatement ps = conn.prepareStatement("select * from customers where accno = ?");
			ps.setString(1, accNo);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				String a = rs.getString("accno");
				String u = rs.getString("username");
				String p = rs.getString("password");
				String m = rs.getString("mobile");

				customer = new Customer(a, u, m, p);

			} else {
				throw new CustomerException("Customer does not exists with acc no " + accNo);
			}

		} catch (SQLException e) {
			e.printStackTrace();

			throw new CustomerException(e.getMessage());
		}

		return customer;

	}

	@Override
	public Customer loginCustomer(String username, String password) throws CustomerException {
		Customer customer = null;

		try (Connection conn = DBUtil.provideConnection()) {

			PreparedStatement ps = conn.prepareStatement("select * from customers where username = ? AND password = ?");

			ps.setString(1, username);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				String a = rs.getString("accno");
				String u = rs.getString("username");
				String p = rs.getString("password");
				String m = rs.getString("mobile");

				customer = new Customer(a, u, m, p);

			} else {

				throw new CustomerException("Invalid username or password");

			}

		} catch (SQLException e) {
			throw new CustomerException(e.getMessage());
		}

		return customer;
	}

	@Override
	public List<Customer> getAllCustomersDetails() throws CustomerException {
		List<Customer> customersList = new ArrayList<>();

		try (Connection conn = DBUtil.provideConnection()) {

			PreparedStatement ps = conn.prepareStatement("select * from customers");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String a = rs.getString("accno");
				String u = rs.getString("username");
				String p = rs.getString("password");
				String m = rs.getString("mobile");

				Customer customer = new Customer(a, u, m, p);
				customersList.add(customer);

			}
			if(customersList.size()==0) {
				throw new CustomerException("No any customer found..");
			}

		} catch (SQLException e) {
			throw new CustomerException(e.getMessage());
		}

		return customersList;
	}

}
