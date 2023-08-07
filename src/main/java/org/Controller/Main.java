package org.Controller;


import org.Model.User;
import org.Model.Listing;
import org.Model.Amenity;
import org.Model.ListingAmenity;
import org.Model.Booking;
import org.Model.Rating;
import org.Model.Availability;
import org.Model.Dates;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import org.Model.DateParser;
import static org.Model.DateParser.formatDate;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

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
        	}
        	else if (choice == 2) {
        		Main.host(user, scanner);
        	}
        	else if (choice == 3) {
        		
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
    
    public static void host(User user, Scanner scanner) {
    	while (true) {
    		System.out.println("------Host-------");
    		System.out.println("Please select an option");
        	System.out.println("(1) Create new Listing\n(2) Cancel a Listing\n(3) Edit a Listing\n(4) Exit");
        	int choice = scanner.nextInt();
        	if (choice == 1) {
        		Main.createListing(user, scanner);
        	} else if (choice == 2) {
        		Main.deleteListing(user, scanner);
        	} else if (choice == 3) {
        		Main.editListing(user, scanner);
        	} else if (choice == 4) {
        		System.out.println("Returning to menu...");
        		break;
        	}
    	} 
    }

	public static void deleteListing(User user, Scanner scanner) {
		System.out.println("------Delete Listing-------");
    	System.out.println("Please enter the listingId:");
		int listingId = scanner.nextInt();
		Listing listing = ListingController.getListing(listingId);
		if (listing == null) {
			System.out.println("Error: Could not find listing with that listingId.");
			return;
		}
		if (listing.getHostId() != user.getUserId()) {
			System.out.println("Error: You are not the host of this listing.");
			return;
		}
		ListingController.deleteListing(listingId);
		System.out.println("Successfully deleted listing.");
	}
    
    public static void editListing(User user, Scanner scanner) {
    	System.out.println("------Edit Listing-------");
    	System.out.println("Please enter the listingId:");
		int listingId = scanner.nextInt();
		Listing listing = ListingController.getListing(listingId);
		if (listing == null) {
			System.out.println("Error: Could not find listing with that listingId.");
			return;
		}
		if (listing.getHostId() != user.getUserId()) {
			System.out.println("Error: You are not the host of this listing.");
			return;
		}
		System.out.println("Listing found. Please update the listing information.");
		System.out.println("Please enter the type of building:");
		String type = scanner.next();
		System.out.println("Please enter the longitude of the listing:");
		float longitude = scanner.nextFloat();
		System.out.println("Please enter the latitude of the listing:");
		float latitude = scanner.nextFloat();
		System.out.println("Please enter the postal code of the listing:");
		String postalCode = scanner.next();
		System.out.println("Please enter the country of the listing (enter '_' instead of spaces):");
		String country = scanner.next();
		System.out.println("Please enter the city of the listing (enter '_' instead of spaces):");
		String city = scanner.next();
		System.out.println("Please enter the address of the listing (enter '_' instead of spaces):");
		String address = scanner.next();
		Listing newListing = new Listing(0, user.getUserId(), type, longitude, latitude, postalCode, country, city, address);
		ListingController.editListing(newListing);
		System.out.println("Successfully updated listing!");
    }
    
    public static void createListing(User user, Scanner scanner) {
    	System.out.println("------Create New Listing-------");
    	System.out.println("Please enter the type of building:");
		String type = scanner.next();
		System.out.println("Please enter the longitude of the listing:");
		float longitude = scanner.nextFloat();
		System.out.println("Please enter the latitude of the listing:");
		float latitude = scanner.nextFloat();
		System.out.println("Please enter the postal code of the listing:");
		String postalCode = scanner.next();
		System.out.println("Please enter the country of the listing (enter '_' instead of spaces):");
		String country = scanner.next();
		System.out.println("Please enter the city of the listing (enter '_' instead of spaces):");
		String city = scanner.next();
		System.out.println("Please enter the address of the listing (enter '_' instead of spaces):");
		String address = scanner.next();
		Listing listing = new Listing(0, user.getUserId(), type, longitude, latitude, postalCode, country, city, address);
		ListingController.addListing(listing);
		
		while (true) {
			System.out.println("Please enter an amenity in the building, or enter \"exit\" to stop (enter '_' instead of spaces):");
			String amenityName = scanner.next();
			if (amenityName.equals("exit")) {
				break;
			}
			System.out.println("Please enter the quantity:");
			int quantity = scanner.nextInt();
			Amenity amenity = new Amenity(0, amenityName);
			AmenityController.addAmenity(amenity);
			ListingAmenity listingAmenity = new ListingAmenity(listing.getListingId(), amenity.getAmenityId(), quantity);
			ListingAmenityController.addListingAmenity(listingAmenity);
			System.out.println("Successfully added " + quantity + " of " + amenityName +".");
		}
		System.out.println("Please enter a start date for your listing (in YYYY-MM-DD format):");
		String startDate = scanner.next();
		System.out.println("Please enter an end date for your listing (in YYYY-MM-DD format):");
		String endDate = scanner.next();
		System.out.println("Please enter a price for your listing (per day, in $):");
		int price = scanner.nextInt();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date startDate2 = sdf.parse(startDate);
	        Date endDate2 = sdf.parse(endDate);
	        
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(startDate2);
	        
	        while (!calendar.getTime().after(endDate2)) {
	            String currentDateStr = sdf.format(calendar.getTime());
	            //System.out.println(currentDateStr);
	            String currentDate = formatDate(currentDateStr);
	            Availability availability = new Availability(listing.getListingId(), currentDate, true, price);
	            AvailabilityController.addAvailability(availability);
	            calendar.add(Calendar.DAY_OF_MONTH, 1);
	        }
		} catch (ParseException e) {
            e.printStackTrace();
        }
		
		System.out.println("Successfully created a new listing! The listingId is: " + listing.getListingId());
    }
    
    public static void rent(User user, Scanner scanner) {
    	
    	while (true) {
    		System.out.println("------Rent-------");
    		System.out.println("Please select an option");
        	System.out.println("(1) Find and Book\n(2) Cancel a Booking\n(3) Exit");
        	int choice = scanner.nextInt();
        	if (choice == 1) {
        		Main.findAndBook(user, scanner);
        	} else if (choice == 2) {
        		
        	} else if (choice == 3) {
        		System.out.println("Returning to menu...");
        		break;
        	}
    	} 
    	
    }
    
    public static void findAndBook(User user, Scanner scanner) {
    	System.out.println("------Find and Book-------");
    	while (true) {
    		System.out.println("Select how you would like to locate a listing");
        	System.out.println("(1) Longitude and Latitude\n(2) City\n (3) Country\n(4) Postal Code\n(5) Return");
        	int choice = scanner.nextInt();

        	if (choice == 1) {
        		System.out.println("Please enter a longitude: ");
        		float longitude = scanner.nextFloat();
        		System.out.println("Please enter a latitude: ");
        		float latitude = scanner.nextFloat();
        		System.out.println("Please enter a distance range (in km): ");
        		float distance = scanner.nextFloat();
				System.out.println("Please enter the minimum price (enter '0' to not set one): ");
        		int minPrice = scanner.nextInt();
				System.out.println("Please enter the maximum price (enter '0' to not set one): ");
        		int maxPrice = scanner.nextInt();
				System.out.println("Please enter a start date (use YYYY-MM-DD format, or enter 'n' to leave empty)");
        		String startDate = scanner.next();
				System.out.println("Please enter a start date (use YYYY-MM-DD format, or enter 'n' to leave empty)");
        		String endDate = scanner.next();

				if (startDate.equals("n")){
					startDate = null;
				} else {
					startDate = formatDate(startDate);
				}
				if (endDate.equals("n")){
					endDate = null;
				} else {
					endDate = formatDate(endDate);
				}
				
				List<String> amenityList = new ArrayList<>();
				while (true){
					System.out.println("Please enter an amenity you would like to include (enter 'exit' to stop)");
					String amenity = scanner.next();
					if (amenity.equals("exit")){
						break;
					}
					amenityList.add(amenity);
				}
        		
				List<Dates> searchListings = QueriesController.getListingWithinDistance(longitude, latitude, distance, false, true, startDate, endDate, minPrice, maxPrice, amenityList);
				
        		while (true) {
	        		System.out.println("Listings:\n--------");
	        		System.out.println(searchListings);
	        		System.out.println("--------");
	        		System.out.println("Please enter the listingId that you would like to rent, or '0' to exit:");
	        		int listingId = scanner.nextInt();
	        		if (listingId == 0) {
	        			System.out.println("Exiting...");
	        			break;
	        		} else {
	        			Listing listing = ListingController.getListing(listingId);
	        			System.out.println("Listing details\n--------------");
	        			// Bring out listing details
	        			System.out.println("Enter '1' to book, or any other number to leave:");
	        			choice = scanner.nextInt();
	        			if (choice == 1) {
	        				System.out.println("Please enter a start date for your booking:");
	        				String bookingStartDate = scanner.next();
	        				System.out.println("Please enter an end date for your booking:");
	        				String bookingEndDate = scanner.next();
	        				//Check availability
	        				System.out.println("Please enter your credit card number:");
	        				String cardNumber = scanner.next();
	        				//Add booking
	        				System.out.println("Successfully booked!");
	        				break;
	        			} else {
	        				System.out.println("Returning to list...");
	        			}
	        			
	        		}
        		}
        		
        		continue;
        	}
        	else if (choice == 2) {
        		System.out.println("Please enter a city: ");
        		String city = scanner.next();
        		
        		while (true) {
	        		System.out.println("Listings:\n--------");
	        		
	        		//Bring out a list of lists here
	        		System.out.println("--------");
	        		System.out.println("Please enter the listingId that you would like to rent, or '0' to exit:");
	        		int listingId = scanner.nextInt();
	        		
	        		if (listingId == 0) {
	        			System.out.println("Exiting...");
	        			break;
	        		} else {
	        			Listing listing = ListingController.getListing(listingId);
	        			System.out.println("Listing details\n--------------");
	        			//Insert listing info
	        			System.out.println("Enter '1' to book, or any other number to leave:");
	        			choice = scanner.nextInt();
	        			if (choice == 1) {
	        				System.out.println("Please enter a start date for your booking:");
	        				String startDate = scanner.next();
	        				System.out.println("Please enter an end date for your booking:");
	        				String endDate = scanner.next();
	        				//Check availability
	        				System.out.println("Please enter your credit card number:");
	        				String cardNumber = scanner.next();
	        				//Add booking
	        				System.out.println("Successfully booked!");
	        				break;
	        			} else {
	        				System.out.println("Returning to list...");
	        			}
	        		}
        		}
        		continue;	
        	}
        	else if (choice == 3) {
        		System.out.println("Please enter a country: ");
        		String country = scanner.next();
        		
        		while (true) {
	        		System.out.println("Listings:\n--------");
	        		
	        		//Bring out a list of lists here
	        		System.out.println("--------");
	        		System.out.println("Please enter the listingId that you would like to rent, or '0' to exit:");
	        		int listingId = scanner.nextInt();
	        		
	        		if (listingId == 0) {
	        			System.out.println("Exiting...");
	        			break;
	        		} else {
	        			Listing listing = ListingController.getListing(listingId);
	        			System.out.println("Listing details\n--------------");
	        			//Insert listing info
	        			System.out.println("Enter '1' to book, or any other number to leave:");
	        			choice = scanner.nextInt();
	        			if (choice == 1) {
	        				System.out.println("Please enter a start date for your booking:");
	        				String startDate = scanner.next();
	        				System.out.println("Please enter an end date for your booking:");
	        				String endDate = scanner.next();
	        				//Check availability
	        				System.out.println("Please enter your credit card number:");
	        				String cardNumber = scanner.next();
	        				//Add booking
	        				System.out.println("Successfully booked!");
	        				break;
	        			} else {
	        				System.out.println("Returning to list...");
	        			}
	        		}
        		}
        		continue;	
        	}
        	else if (choice == 4) {
        		System.out.println("Please enter a postal code: ");
        		String postalCode = scanner.next();
        		while (true) {
	        		System.out.println("Listings:\n--------");
	        		
	        		//Bring out a list of lists here
	        		System.out.println("--------");
	        		System.out.println("Please enter the listingId that you would like to rent, or '0' to exit:");
	        		int listingId = scanner.nextInt();
	        		
	        		if (listingId == 0) {
	        			System.out.println("Exiting...");
	        			break;
	        		} else {
	        			Listing listing = ListingController.getListing(listingId);
	        			System.out.println("Listing details\n--------------");
	        			//Insert listing info
	        			System.out.println("Enter '1' to book, or any other number to leave:");
	        			choice = scanner.nextInt();
	        			if (choice == 1) {
	        				System.out.println("Please enter a start date for your booking:");
	        				String startDate = scanner.next();
	        				System.out.println("Please enter an end date for your booking:");
	        				String endDate = scanner.next();
	        				//Check availability
	        				System.out.println("Please enter your credit card number:");
	        				String cardNumber = scanner.next();
	        				//Add booking
	        				System.out.println("Successfully booked!");
	        				break;
	        			} else {
	        				System.out.println("Returning to list...");
	        			}
	        		}
        		}
        		continue;
        	}
        	else if (choice == 5) {
        		System.out.println("Returning to rent menu...");
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
}