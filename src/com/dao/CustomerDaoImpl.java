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
import com.bean.Transactions;
import com.exceptions.AccountantException;
import com.exceptions.CustomerException;
import com.mysql.cj.exceptions.UnsupportedConnectionStringException;
import com.usecases.GetAllCustomerUseCase;
import com.usecases.RegisterCustomerUseCase1;
import com.utility.DBUtil;

public class CustomerDaoImpl implements CustomerDao {

	@Override
	public String registerCustomer(Customer customer) {
		String message = "Not inserted..";

		try (Connection conn = DBUtil.provideConnection()) {

			PreparedStatement ps = conn.prepareStatement("insert into customers values(?,?,?,?,?)");
			ps.setString(1, customer.getAccNo());
			ps.setString(2, customer.getUsername());
			ps.setString(3, customer.getPassword());
			ps.setString(4, customer.getMobile());
			ps.setInt(5, customer.getBalance());

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
	public String registerCustomer(String username, String password, String mobile, int balance) {
		// Generating 12 digit account number
		Random rnd = new Random();
		int number = rnd.nextInt(999999999);
		String accNo = String.format("%9d", number);
		accNo = accNo + "000";

		String message = "Not inserted..";

		try (Connection conn = DBUtil.provideConnection()) {

			PreparedStatement ps = conn.prepareStatement("insert into customers values(?,?,?,?,?)");
			ps.setString(1, accNo);
			ps.setString(2, username);
			ps.setString(3, password);
			ps.setString(4, mobile);
			ps.setInt(5, balance);

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
				int b = rs.getInt("balance");

				customer = new Customer(a, u, m, p);
				customer.setBalance(b);

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
				int b = rs.getInt("balance");

				Customer customer = new Customer(a, u, m, p);
				customer.setBalance(b);
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
		boolean flag = true;
		while (flag) {

			try (Connection conn = DBUtil.provideConnection()) {

				PreparedStatement ps = conn
						.prepareStatement("select * from accountants where username = ? AND password = ?");

				ps.setString(1, username);
				ps.setString(2, password);

				ResultSet rs = ps.executeQuery();

				if (rs.next()) {

					String u = rs.getString("username");

					System.out
							.println("Accountant logged in Successfully\n Welcome, " + u + "\n Select below Options:");
					System.out.println("1.Add new account for customer");
					System.out.println("2.Edit already available account");
					System.out.println("3.Remove account by using account number");
					System.out.println("4.View account details by account number");
					System.out.println("5.View transaction history by account number");
					System.out.println("6.View all accounts details");
					System.out.println("7.View all Transactions History");
					System.out.println("8.Previous Menu");

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
						System.out.println("Enter account number to check transaction history:");
						String accountno = sc.next();
						checkTransactionHistory(accountno);
						break;
					case 6:
						GetAllCustomerUseCase.main1();
						break;
					case 7:
						try {
							showAllTransactions();

						} catch (CustomerException ce) {

							System.out.println(ce.getMessage());
						}
						break;
					case 8:
						flag = false;
						break;
					default:
						System.out.println("Invalid entry please choose from 1 to 8");
					}

				} else {

					throw new AccountantException("Invalid username or password");

				}

			} catch (SQLException se) {
				throw new AccountantException(se.getMessage());
			}
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
			if (x > 0) {
				System.out.println("Account removed successfully");
			} else {
				System.out.println("Account not exists");
			}

		} catch (SQLException se) {
			System.out.println(se.getMessage());
		}

	}

	@Override
	public void loginCustomer1(String username, String password) throws CustomerException {
		String message = "Invalid username of password";
		boolean flag = true;
		while (flag) {
			try (Connection conn = DBUtil.provideConnection()) {

				PreparedStatement ps = conn
						.prepareStatement("select * from customers where username = ? AND password = ?");

				ps.setString(1, username);
				ps.setString(2, password);

				ResultSet rs = ps.executeQuery();

				if (rs.next()) {

					String u = rs.getString("username");
					String accno = rs.getString("accno");

					System.out.println("Customer logged in Successfully\n Welcome, " + u + "\n Select below Options:");
					System.out.println("1.Deposite");
					System.out.println("2.Withdraw");
					System.out.println("3.Transfer");
					System.out.println("4.Check Transaction History");
					System.out.println("5.Previous Menu");

					Scanner sc = new Scanner(System.in);
					int choice = sc.nextInt();

					switch (choice) {
					case 1:
						System.out.println("Enter amount to deposit:");
						int amount = sc.nextInt();
						depositeMoney(amount, accno);
						break;
					case 2:
						System.out.println("Enter amount to withdraw:");
						int amount1 = sc.nextInt();
						withdrawMoney(amount1, accno);
						break;
					case 3:
						System.out.println("Enter Receiver's account Number:");
						String raccno = sc.next();
						System.out.println("Enter Amount to transfer:");
						int a = sc.nextInt();
						transferMoney(a, accno, raccno);
						break;
					case 4:
						checkTransactionHistory(accno);
						break;
					case 5:
						flag = false;
						break;
					default:
						System.out.println("Invalid entry please choose option 1 or 5");
					}

				} else {

					throw new CustomerException("Invalid username or password");

				}

			} catch (SQLException ce) {
				throw new CustomerException(ce.getMessage());
			}
		}

	}

	@Override
	public void depositeMoney(int amount, String accno) throws CustomerException {

		try (Connection conn = DBUtil.provideConnection()) {

			PreparedStatement ps = conn.prepareStatement("update customers set balance=balance+? where accno=?");

			ps.setInt(1, amount);
			ps.setString(2, accno);

			int x = ps.executeUpdate();
			if (x > 0) {
				System.out.println(amount + " Deposited succesfully");
			} else {
				System.out.println(amount + " Not deposited");
			}

			Customer customer = getCustomerByAcc(accno);
			int balance = customer.getBalance();

			PreparedStatement ps1 = conn
					.prepareStatement("insert into transactions(accno,credit,available_balance) values(?,?,?)");

			ps1.setString(1, accno);
			ps1.setInt(2, amount);
			ps1.setInt(3, balance);

			ps1.executeUpdate();//

		} catch (SQLException e) {
			System.out.println(e.getMessage());

		}

	}

	@Override
	public void withdrawMoney(int amount, String accno) throws CustomerException {

		try (Connection conn = DBUtil.provideConnection()) {

			Customer customer = getCustomerByAcc(accno);

			int balance = customer.getBalance();

			if (balance > amount || balance == amount) {
				balance = balance - amount;
				customer.setBalance(balance);

				PreparedStatement ps = conn.prepareStatement("update customers set balance = ? where accno=?");
				ps.setInt(1, balance);
				ps.setString(2, accno);

				int x = ps.executeUpdate();
				if (x > 0) {
					System.out.println(amount + "Rs Withdrawal successfull");
				} else {
					System.out.println(amount + "Rs Withdrawal fail");
				}

				customer = getCustomerByAcc(accno);
				balance = customer.getBalance();

				PreparedStatement ps1 = conn
						.prepareStatement("insert into transactions(accno,debit,available_balance) values(?,?,?)");

				ps1.setString(1, accno);
				ps1.setInt(2, amount);
				ps1.setInt(3, balance);

				ps1.executeUpdate();

			} else {
				System.out.println("No sufficient balance to withdraw");
				System.out.println("Your balance is :" + balance);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());

		}

	}

	@Override
	public void transferMoney(int amount, String accno, String raccno) throws CustomerException {

		try (Connection conn = DBUtil.provideConnection()) {

			Customer rcustomer = getCustomerByAcc(raccno);
			Customer acustomer = getCustomerByAcc(accno);

			int abalance = acustomer.getBalance();
			int rbalance = rcustomer.getBalance();

			if (abalance > amount || abalance == amount) {
				abalance = abalance - amount;
				rbalance = rbalance + amount;
				rcustomer.setBalance(rbalance);

				PreparedStatement aps = conn.prepareStatement("update customers set balance = ? where accno=?");
				PreparedStatement rps = conn.prepareStatement("update customers set balance = ? where accno=?");
				aps.setInt(1, abalance);
				aps.setString(2, accno);
				rps.setInt(1, rbalance);
				rps.setString(2, raccno);

				int x = aps.executeUpdate();
				int y = rps.executeUpdate();
				if (x > 0 && y > 0) {
					System.out.println(amount + "Rs debited from your account");
					System.out.println("and credited to " + " " + raccno + " " + " account");
					System.out.println("Available Balance in your acc is " + abalance);
				} else {
					System.out.println(amount + "Rs Withdrawal fail");
				}

			} else {
				System.out.println("No sufficient balance to transfer");
				System.out.println("Your balance is :" + abalance);
			}

			Customer customer = getCustomerByAcc(accno);
			abalance = customer.getBalance();

			PreparedStatement aps1 = conn
					.prepareStatement("insert into transactions(accno,debit,available_balance) values(?,?,?)");

			aps1.setString(1, accno);
			aps1.setInt(2, amount);
			aps1.setInt(3, abalance);

			aps1.executeUpdate();

			customer = getCustomerByAcc(raccno);
			rbalance = customer.getBalance();
			PreparedStatement rps1 = conn
					.prepareStatement("insert into transactions(accno,credit,available_balance) values(?,?,?)");

			rps1.setString(1, raccno);
			rps1.setInt(2, amount);
			rps1.setInt(3, rbalance);

			rps1.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());

		}

	}

	@Override
	public void showAllTransactions() throws CustomerException {
		List<Transactions> transactions = new ArrayList<>();

		try (Connection conn = DBUtil.provideConnection()) {
			PreparedStatement ps = conn.prepareStatement("select * from transactions");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String a = rs.getString("accno");
				int d = rs.getInt("debit");
				int c = rs.getInt("credit");
				int ab = rs.getInt("available_balance");

				Transactions t = new Transactions(a, d, c, ab);
				transactions.add(t);

			}

			if (transactions.size() == 0) {
				System.out.println("No transactions available");
			} else {
				transactions.forEach(t -> System.out.println(t));
			}

		} catch (SQLException se) {
			System.out.println(se.getMessage());
		}
	}

	@Override
	public void checkTransactionHistory(String accno) {
		List<Transactions> transactions = new ArrayList<>();

		try (Connection conn = DBUtil.provideConnection()) {
			PreparedStatement ps = conn.prepareStatement("select * from transactions where accno=?");
			ps.setString(1, accno);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				String a = rs.getString("accno");
				int d = rs.getInt("debit");
				int c = rs.getInt("credit");
				int avl_bal = rs.getInt("available_balance");

				Transactions t = new Transactions(a, d, c, avl_bal);

				transactions.add(t);
			}

			if (transactions.size() == 0) {
				System.out.println("No any transaction done");
			} else {
				transactions.forEach(t -> System.out.println(t));
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
