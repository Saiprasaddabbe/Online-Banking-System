package com.App;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		
		
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Welcome to the Online Banking System:");
		System.out.println("Choose the following Option:");
		System.out.println("1.Log in as a Accountat");
		System.out.println("2.Log in as a Customer");
		System.out.println("3.New Customer? Register..!");
	
		int choice = sc.nextInt();
		
		switch (choice) {
		case 1:
	
		    break;
		case 2:
			System.out.println("hi customer");
			break;
		case 3:
			System.out.println("register");
			break;
		default:
			System.out.println("Invalid Entry...Please Press valid option 1,2 or 3");
		}
		
		
		
		
		
		

	}

}
