package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.bean.Customer;
import com.exceptions.AccountantException;
import com.exceptions.CustomerException;
import com.usecases.GetAllCustomerUseCase;
import com.usecases.RegisterCustomerUseCase1;
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
			if (customersList.size() == 0) {
				throw new CustomerException("No any customer found..");
			}

		} catch (SQLException e) {
			throw new CustomerException(e.getMessage());
		}

		return customersList;
	}

	@Override
	public void loginAccountant(String username, String password) throws AccountantException {
		String message = "Invalid username of password";

		try (Connection conn = DBUtil.provideConnection()) {

			PreparedStatement ps = conn
					.prepareStatement("select * from accountants where username = ? AND password = ?");

			ps.setString(1, username);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				String u = rs.getString("username");

				System.out.println("Accountant logged in Successfully\n Welcome, " + u + "\n Select below Options:");
				System.out.println("1.Add new account for customer");
				System.out.println("2.Edit already available account");
				System.out.println("3.Remove account by using account number");
				System.out.println("4.View account details by account number");
				System.out.println("5.View all accounts details");

				Scanner sc = new Scanner(System.in);
				int choice = sc.nextInt();

				switch (choice) {
				case 1:
					RegisterCustomerUseCase1.main1();
					break;
				case 2:
					System.out.println("Enter Account number to update details:");
					String accno = sc.next();
					try {
						updateCustomersDetails(accno);
					} catch (CustomerException ce) {

						System.out.println(ce.getMessage());
					}
					break;
				case 3:
					System.out.println("Enter Account number to remove:");
					String acno = sc.next();
					try {
						removeCustomerAccount(acno);
					} catch (CustomerException ce) {
						// TODO Auto-generated catch block
						System.out.println(ce.getMessage());
					}
					break;
				case 4:
					System.out.println("Enter Account number to view cutomer details");
					String accno1 = sc.next();
					try {
						Customer customer = getCustomerByAcc(accno1);
						System.out.println(customer);
					} catch (CustomerException ce) {
						System.out.println(ce.getMessage());
					}
					break;
				case 5:
					
					GetAllCustomerUseCase.main1();
					
					
					break;
				default:
					System.out.println("Invalid entry please choose from 1 to 5");
				}

			} else {

				throw new AccountantException("Invalid username or password");

			}

		} catch (SQLException se) {
			throw new AccountantException(se.getMessage());
		}

	}

	@Override
	public void updateCustomersDetails(String accNo) throws CustomerException {

		Scanner sc = new Scanner(System.in);
		try (Connection conn = DBUtil.provideConnection()) {

			PreparedStatement ps = conn.prepareStatement("select * from customers where accno = ?");
			ps.setString(1, accNo);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				String a = rs.getString("accno");
				String u = rs.getString("username");
				String p = rs.getString("password");
				String m = rs.getString("mobile");

				System.out.println("Current Details :-");
				System.out.println("accno: " + a + "\nusername: " + u + "\npassword: " + p + "\nmobile: " + m);
				System.out.println("==========================");
				System.out.println("Enter new Username:");
				String uname = sc.next();
				System.out.println("Enter new password:");
				String pass = sc.next();
				System.out.println("Enter new mobile:");
				String mob = sc.next();
				PreparedStatement ps1 = conn
						.prepareStatement("update customers set username = ?,mobile=?,password=? where accno=?");
				ps1.setString(1, uname);
				ps1.setString(2, mob);
				ps1.setString(3, pass);
				ps1.setString(4, accNo);

				int x = ps1.executeUpdate();

				if (x > 0) {
					System.out.println("Information updated successfully");
				} else {
					System.out.println("Information not updated");
				}

			} else {
				throw new CustomerException("Customer does not exists with acc no " + accNo);
			}

		} catch (SQLException e) {
			e.printStackTrace();

			throw new CustomerException(e.getMessage());
		}

	}

	@Override
	public void removeCustomerAccount(String accNo) throws CustomerException {

		try (Connection conn = DBUtil.provideConnection()) {

			PreparedStatement ps = conn.prepareStatement("delete from customers where accno = ? ");
			ps.setString(1, accNo);

			Scanner sc = new Scanner(System.in);

   
            	int x = ps.executeUpdate();
            	if(x>0) {
            		System.out.println("Account removed successfully");
            	}else {
            		System.out.println("Account not exists");
            	}
            	
          

		} catch (SQLException se) {
			System.out.println(se.getMessage());
		}

	}

	@Override
	public void loginCustomer1(String username, String password) throws CustomerException {
		String message = "Invalid username of password";

		try (Connection conn = DBUtil.provideConnection()) {

			PreparedStatement ps = conn
					.prepareStatement("select * from customers where username = ? AND password = ?");

			ps.setString(1, username);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				String u = rs.getString("username");

				System.out.println("Customer logged in Successfully\n Welcome, " + u + "\n Select below Options:");
				System.out.println("1.Transfer Money");
				System.out.println("2.Check Transaction History");
				
				
				Scanner sc = new Scanner(System.in);
				int choice = sc.nextInt();

				switch (choice) {
				case 1:
					//transfer money method
					break;
				case 2:
					//transaction history method
					break;
				default:
					System.out.println("Invalid entry please choose option 1 or 2");
				}

			} else {

				throw new CustomerException("Invalid username or password");

			}

		} catch (SQLException ce) {
			throw new CustomerException(ce.getMessage());
		}

	}

}
