package org.Controller;


import org.Model.User;
import org.Model.Listing;

import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

	public static void main(String[] args) {
		JdbcSqlServerConnection.connectToServer();
		JdbcSqlServerConnection.initDatabase();
		Scanner scanner = new Scanner(System.in);

		User user = Main.entry(scanner);
		if (user == null) {
			JdbcSqlServerConnection.closeConnection();
			return;
		}
		System.out.println("-------------------");

		while (true) {
			System.out.println("Please select an option, "+user.getName());
			System.out.println("(1) Rent\n(2) Host\n(3) Rate\n(4) Exit");
			int choice = scanner.nextInt();

			if (choice == 1) {
				Main.rent(user, scanner);
				break;
			}
			else if (choice == 2) {
				break;
			}
			else if (choice == 3) {
				break;
			}
			else if (choice == 4) {
				System.out.println("Thank you for using WindNbN.");
				System.out.println("Exiting...");
				break;
			} else {
				System.out.println("Invalid input\n");
			}
		}
		scanner.close();
		JdbcSqlServerConnection.closeConnection();
	}

	public static void rent(User user, Scanner scanner) {
		System.out.println("------Rent-------");
		while (true) {
			System.out.println("Select how you would like to locate a listing");
			System.out.println("(1) Longitude and Latitude\n(2) City\n (3) Country\n(4) Postal Code\n(5) Exit");
			int choice = scanner.nextInt();

			if (choice == 1) {
				System.out.println("Please enter a longitude: ");
				float longitude = scanner.nextFloat();
				System.out.println("Please enter a latitude: ");
				float latitude = scanner.nextFloat();
				System.out.println("Please enter a range: ");
				float range = scanner.nextFloat();
				System.out.println("Listings:\n--------");

				//Bring out a list of lists here
				System.out.println("--------");
				System.out.println("Please enter the listingId that you would like to rent, or '0' to exit:");
				int listingId = scanner.nextInt();

				if (listingId == 0) {
					System.out.println("Exiting...");
				} else {
					Listing listing = ListingController.getListing(listingId);
					//Insert new booking here
				}

				continue;
			}
			else if (choice == 2) {
				System.out.println("Please enter a city: ");
				String city = scanner.next();
				System.out.println("Listings:\n--------");

				//Bring out a list of lists here
				System.out.println("--------");
				System.out.println("Please enter the listingId that you would like to rent, or '0' to exit:");
				int listingId = scanner.nextInt();

				if (listingId == 0) {
					System.out.println("Exiting...");
				} else {
					Listing listing = ListingController.getListing(listingId);
					//Insert new booking here
				}
				continue;
			}
			else if (choice == 3) {
				System.out.println("Please enter a country: ");
				String country = scanner.next();
				System.out.println("Listings:\n--------");

				//Bring out a list of lists here
				System.out.println("--------");
				System.out.println("Please enter the listingId that you would like to rent, or '0' to exit:");
				int listingId = scanner.nextInt();

				if (listingId == 0) {
					System.out.println("Exiting...");
				} else {
					Listing listing = ListingController.getListing(listingId);
					//Insert new booking here
				}
				continue;
			}
			else if (choice == 4) {
				System.out.println("Please enter a postal code: ");
				String postalCode = scanner.next();
				System.out.println("Listings:\n--------");

				//Bring out a list of lists here
				System.out.println("--------");
				System.out.println("Please enter the listingId that you would like to rent, or '0' to exit:");
				int listingId = scanner.nextInt();

				if (listingId == 0) {
					System.out.println("Exiting...");
				} else {
					Listing listing = ListingController.getListing(listingId);
					//Insert new booking here
				}
				continue;
			}
			else if (choice == 5) {
				System.out.println("Returning to menu...");
				break;
			} else {
				System.out.println("Invalid input\n");
			}
		}
	}


	public static User entry(Scanner scanner) {
		int userId = -1;

		while (true) {
			System.out.println("Welcome to WindNbN\n--------------");
			System.out.println("Please enter one of the following options");
			System.out.println("(1) Sign in\n(2) Sign up");
			int choice = scanner.nextInt();

			while (choice != 1 && choice != 2) {
				System.out.println("Invalid input, please try again");
				choice = scanner.nextInt();
			}


			if (choice == 1) {
				System.out.println("Sign In\n--------------");
				System.out.println("UserId: ");
				userId = scanner.nextInt();
				User user = UserController.getUser(userId);
				if (user != null) {
					System.out.println("Welcome back, " + user.getName());
					return user;
				} else {
					System.out.println("Invalid UserId.");
				}
			} else if (choice == 2) {
				System.out.println("Sign Up\n--------------");
				System.out.println("Please enter your occupation (all lowercase): ");
				String occupation = scanner.next();
				System.out.println("Please enter your address (all lowercase): ");
				String address = scanner.next();
				System.out.println("Please enter your date of birth (in YYYY-MM-DD format): ");
				String dob = scanner.next();
				System.out.println("Please enter your SIN (numerical only, no special characters): ");
				int sin = scanner.nextInt();
				System.out.println("Please enter your name (this will be your display name): ");
				String name = scanner.next();
				User newUser = new User(0, occupation, address, dob, sin, name);
				UserController.addUser(newUser);
				System.out.println("Registration Complete! Your UserId is: " + newUser.getUserId());
				System.out.println("Please note your UserId, as it will be required for signing in.");
			}
		}
	}
