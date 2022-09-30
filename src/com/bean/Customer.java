package com.bean;

import java.util.Random;

public class Customer {

	private String username;
	private String mobile;
	private String accNo;
	private String password;
	private int balance;

	public Customer() {
		// Generating 12 digit account number
		Random rnd = new Random();
		int number = rnd.nextInt(999999999);
		String accN = String.format("%9d", number);
		accN = accN + "000";

		this.accNo = accN;
	}

	public Customer(String username, String mobile, String password) {
		super();
		this.username = username;
		this.mobile = mobile;
		this.password = password;
		// Generating 12 digit account number
		Random rnd = new Random();
		int number = rnd.nextInt(999999999);
		String accN = String.format("%9d", number);
		accN = accN + "000";

		this.accNo = accN;
	}

	public Customer(String a, String u, String m, String p) {
		super();
		this.username = u;
		this.mobile = m;
		this.password = p;
		this.accNo = a;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Customer [username=" + username + ", mobile=" + mobile + ", accNo=" + accNo + ", password=" + password
				+ ", balance=" + balance + "]";
	}

	

}
