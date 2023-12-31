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
		JdbcSqlServerConnection.runSqlScript("src/main/java/org/Controller/initDB.sql");
		JdbcSqlServerConnection.runSqlScript("src/main/java/org/Controller/testdata.sql");
		Scanner scanner = new Scanner(System.in);
		QueriesController.createDatesWithAmenitiesView();

		User user = Main.entry(scanner);
		if (user == null) {
			JdbcSqlServerConnection.closeConnection();
			return;
		}
		System.out.println("-------------------");

		while (true) {
			System.out.println("Please select an option, "+user.getName());
			System.out.println("(1) Rent\n(2) Host\n(3) Rate\n(4) Report\n(5) Exit");
			int choice = scanner.nextInt();

			if (choice == 1) {
				Main.rent(user, scanner);
			}
			else if (choice == 2) {
				Main.host(user, scanner);
			}
			else if (choice == 3) {
				Main.rate(user, scanner);
			}
			else if (choice == 4) {
				Main.report(user, scanner);
			}
			else if (choice == 5) {
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

	public static void report(User user, Scanner scanner) {
		System.out.println("------Report-------");
		System.out.println("Please select an option");
		System.out.println("(1) Total Bookings in Date Range within a City\n(2) Total Bookings in Date Range via Zip Code\n(3) Total Listings per Country\n(4) Total Listings per Country and City");
		System.out.println("(5) Total Listings per Country, City, and Postal Code\n(6) Rank Hosts by Country\n(7) Rank Hosts by Country and City");
		System.out.println("(8) Rank Hosts and Renters by Cancellations\n(9) Popular Noun Phrases");
		System.out.println("Enter any other number to exit.");
		int choice = scanner.nextInt();
		scanner.nextLine();
		if (choice == 1) {
			System.out.println("Total Bookings in Date Range within a City");
			System.out.println("-------");
			System.out.println("Enter a city:");
			String city = scanner.nextLine();
			System.out.println("Enter a start date:");
			String startDate = scanner.nextLine();
			System.out.println("Enter an end date:");
			String endDate = scanner.nextLine();
			startDate = formatDate(startDate);
			endDate = formatDate(endDate);
			System.out.println("Total Bookings in "+city+": "+ReportController.getTotalBookings(city, startDate, endDate));
		} else if (choice == 2) {
			System.out.println("Total Bookings in Date Range via Zip Code");
			System.out.println("-------");
			System.out.println("Enter a city:");
			String city = scanner.nextLine();
			System.out.println("Enter a zip code:");
			String zipCode = scanner.nextLine();
			System.out.println("Enter a start date:");
			String startDate = scanner.nextLine();
			System.out.println("Enter an end date:");
			String endDate = scanner.nextLine();
			startDate = formatDate(startDate);
			endDate = formatDate(endDate);
			System.out.println("Total Bookings in "+city+": "+ReportController.getTotalBookingsByZip(city, zipCode, startDate, endDate));
		} else if (choice == 3) {
			System.out.println("Total Listings per Country");
			System.out.println("-------");
			System.out.println("Enter a country:");
			String country = scanner.nextLine();
			System.out.println("Listings: " + ReportController.getTotalListings(country));
			ReportController.getCommercialHosts(country, scanner);
		} else if (choice == 4) {
			System.out.println("Total Listings per Country and City");
			System.out.println("-------");
			System.out.println("Enter a country:");
			String country = scanner.nextLine();
			System.out.println("Enter a city:");
			String city = scanner.nextLine();
			System.out.println("Listings: " + ReportController.getTotalListings(country, city));
			ReportController.getCommercialHosts(country, city, scanner);
		} else if (choice == 5) {
			System.out.println("Total Listings per Country, City, and Postal Code");
			System.out.println("-------");
			System.out.println("Enter a country:");
			String country = scanner.nextLine();
			System.out.println("Enter a city:");
			String city = scanner.nextLine();
			System.out.println("Enter a postal code:");
			String postalCode = scanner.nextLine();
			System.out.println("Listings: " + ReportController.getTotalListings(country, city, postalCode));
			ReportController.getCommercialHosts(country, city, scanner);
		} else if (choice == 6) {
			System.out.println("Rank Hosts by Country");
			System.out.println("-------");
			System.out.println("Enter a country:");
			String country = scanner.nextLine();
			System.out.println("Hosts: ");
			List<int[]> hosts = ReportController.getHostsByCountry(country);
			for (int[] hostInfo : hosts) {
				int hostId = hostInfo[0];
				int numListings = hostInfo[1];
				System.out.println("Host ID: " + hostId + ", Number of Listings: " + numListings);
			}
			ReportController.getCommercialHosts(country, scanner);
		} else if (choice == 7) {
			System.out.println("Rank Hosts by Country and City");
			System.out.println("-------");
			System.out.println("Enter a country:");
			String country = scanner.nextLine();
			System.out.println("Enter a city:");
			String city = scanner.nextLine();
			System.out.println("Hosts: ");
			List<int[]> hosts = ReportController.getHostsByCity(country, city);
			for (int[] hostInfo : hosts) {
				int hostId = hostInfo[0];
				int numListings = hostInfo[1];
				System.out.println("Host ID: " + hostId + ", Number of Listings: " + numListings);
			}
			ReportController.getCommercialHosts(country, city, scanner);

		} else if (choice == 8) {
			System.out.println("Rank Hosts and Renters by Cancellations");
			System.out.println("-------");
			System.out.println("Enter a start date:");
			String startDate = scanner.nextLine();
			System.out.println("Enter an end date:");
			String endDate = scanner.nextLine();
			startDate = formatDate(startDate);
			endDate = formatDate(endDate);
			int[] hosts = ReportController.getMostHostCancellations(startDate, endDate);
			int[] guests = ReportController.getMostGuestCancellations(startDate, endDate);
			System.out.println("Most Host Cancellations: "+hosts);
			System.out.println("Most Renter Cancellations: "+guests);
		} else if (choice == 9) {
			System.out.println("Popular Noun Phrases");
			System.out.println("-------");
			System.out.println("Enter a listingId:");
			int listingId = scanner.nextInt();
			List<String> nouns = ReportController.getNounPhrases(listingId);
			System.out.println("Popular Nouns:");
			for (String noun : nouns) {
				System.out.println(noun);
			}
		} else  {
			System.out.println("Returning to menu...");
			return;
		}

	}

	public static void rate(User user, Scanner scanner) {
		System.out.println("------Rate-------");
		System.out.println("Please select an option");
		System.out.println("(1) Rate a Host\n(2) Rate a Renter\n(3) Check My Ratings\n(4) Exit");
		int choice = scanner.nextInt();
		if (choice == 1) {
			Main.rateHost(user, scanner);
			return;
		} else if (choice == 2) {
			Main.rateRenter(user, scanner);
			return;

		} else if (choice == 2) {
			Main.checkRating(user, scanner);
			return;
		} else if (choice == 4) {
			System.out.println("Returning to menu...");
			return;
		}
		System.out.println("Invalid input, returning to menu...");
	}

	public static void checkRating(User user, Scanner scanner) {
		System.out.println("------Check My Ratings-------");
		System.out.println("Please enter an option, or enter any other to exit");
		System.out.println("(1) Guest Ratings\n(2) Host Ratings\n(3) Exit");
		int choice = scanner.nextInt();

		if (choice == 1) {

		} else if (choice == 2) {

		} else {
			System.out.println("Exiting...");
			return;
		}
	}

	public static void rateHost(User user, Scanner scanner) {
		System.out.println("------Rate a Host-------");
		System.out.println("Please enter the UserId of the host");
		int hostId = scanner.nextInt();
		User host = UserController.getUser(hostId);
		if (host == null) {
			System.out.println("Error: No host found with that ID.");
			return;
		}
		Booking booking = BookingController.bookingFromHost(user.getUserId(), hostId);
		if (booking == null) {
			System.out.println("Error: You have not rented from this host.");
			return;
		}
		System.out.println("Please enter your rating (1-5):");
		int score = scanner.nextInt();
		scanner.nextLine(); // Consume the newline character after reading the int

		System.out.println("Please enter your comment:");
		String comment = scanner.nextLine(); // Read the whole line, including spaces
		booking.setComment(comment);
		booking.setScore(score);
		BookingController.editBooking(booking);
		System.out.println("Successfully added rating.");
	}

	public static void rateRenter(User user, Scanner scanner) {
		System.out.println("------Rate a Renter-------");
		System.out.println("Please enter the UserId of the renter");
		int renterId = scanner.nextInt();
		User renter = UserController.getUser(renterId);
		if (renter == null) {
			System.out.println("Error: No renter found with that ID.");
			return;
		}
		Booking booking = BookingController.bookingFromRenter(user.getUserId(), renterId);
		if (booking == null) {
			System.out.println("Error: This user has not rented from you.");
			return;
		}
		System.out.println("Please enter your rating (1-5):");
		int score = scanner.nextInt();
		System.out.println("Please enter your comment:");
		String comment = scanner.next();
		Rating rating = new Rating(renterId, user.getUserId(), score, booking.getEndDate(), comment);
		RatingController.addRating(rating);
		System.out.println("Successfully added rating.");
	}

	public static void host(User user, Scanner scanner) {
		while (true) {
			System.out.println("------Host-------");
			System.out.println("Please select an option");
			System.out.println("(1) Create new Listing\n(2) Cancel a Listing\n(3) Edit a Listing Price\n(4) Exit");
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
		System.out.println("------Cancel Listing-------");
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
		System.out.println("Please enter the date you would like to cancel:");
		scanner.nextLine();
		String date = scanner.next();
		date = formatDate(date);
		Availability availability = AvailabilityController.getAvailability(listingId, date);
		if (availability == null) {
			System.out.println("Error: Invalid date.");
			return;
		}
		Booking booking = BookingController.findBookingByListing(listingId, date);
		if (booking != null) {
			booking.setCancelledBy(user.getUserId());
			BookingController.editBooking(booking);
		}
		availability.setAvailable(false);
		AvailabilityController.editAvailability(availability);
		user.setHostCancelCount(user.getHostCancelCount() + 1);
		UserController.editUser(user);
		System.out.println("Successfully cancelled listing for" + date +".");
	}

	public static void editListing(User user, Scanner scanner) {
		System.out.println("------Edit Listing Price-------");
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
		System.out.println("Listing found. Please enter the date you would like to change (YYYY-MM-DD format).");
		scanner.nextLine();
		String date = scanner.next();
		date = formatDate(date);
		Availability availability = AvailabilityController.getAvailability(listingId, date);
		if (availability == null) {
			System.out.println("Error: Invalid date.");
			return;
		}
		if (availability.isAvailable() == false) {
			System.out.println("Error: This date has already been booked, cannot change price.");
			return;
		}
		System.out.println("Please enter the new price for this date:");
		int price = scanner.nextInt();
		availability.setPrice(price);
		AvailabilityController.editAvailability(availability);
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
		scanner.nextLine();
		String postalCode = scanner.nextLine();
		System.out.println("Please enter the country of the listing:");
		String country = scanner.nextLine();
		System.out.println("Please enter the city of the listing:");
		String city = scanner.nextLine();
		System.out.println("Please enter the address of the listing:");
		String address = scanner.nextLine();
		Listing listing = new Listing(0, user.getUserId(), type, longitude, latitude, postalCode, country, city, address);
		ListingController.addListing(listing);

		int estimatedPrice = 50;

		if (type.equals("apartment")) {
			estimatedPrice = 30;
		} else if (type.equals("house")) {
			estimatedPrice = 90;
		} else if (type.equals("cabin")) {
			estimatedPrice = 70;
		}

		while (true) {
			System.out.println("Please enter an amenity in the building, or enter \"exit\" to stop (enter '_' instead of spaces):");
			System.out.println("Consider adding the following amenities: 'Bedroom', 'Bathroom', 'Kitchen', 'Pool', 'Wifi'");
			String amenityName = scanner.next();
			if (amenityName.equals("exit")) {
				break;
			}
			System.out.println("Please enter the quantity:");
			int quantity = scanner.nextInt();

			Amenity amenity = null;
			if (AmenityController.getAmenity(amenityName) != null) {
				amenity = AmenityController.getAmenity(amenityName);
				if (amenity.getName().equals("bedroom")) {
					estimatedPrice += 50 * quantity;
				} else if (amenity.getName().equals("wifi")) {
					estimatedPrice += 35 * quantity;
				} else if (amenity.getName().equals("bathroom")) {
					estimatedPrice += 25 * quantity;
				} else if (amenity.getName().equals("kitchen")) {
					estimatedPrice += 40 * quantity;
				} else if (amenity.getName().equals("pool")) {
					estimatedPrice += 75 * quantity;
				}
			} else {
				amenity = new Amenity(0, amenityName);
				AmenityController.addAmenity(amenity);
				estimatedPrice += 20 * quantity;
			}
			ListingAmenity listingAmenity = new ListingAmenity(listing.getListingId(), amenity.getAmenityId(), quantity);
			ListingAmenityController.addListingAmenity(listingAmenity);
			System.out.println("Successfully added " + quantity + " of " + amenityName +".");
		}
		System.out.println("Please enter a start date for your listing (in YYYY-MM-DD format):");
		String startDate = scanner.next();
		System.out.println("Please enter an end date for your listing (in YYYY-MM-DD format):");
		String endDate = scanner.next();
		System.out.println("Please enter a price for your listing (per day, in $):");
		System.out.println("According to the amenities added, we recommend the price of $" + estimatedPrice);
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
				Main.cancelBooking(user, scanner);
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
			System.out.println("(1) Longitude and Latitude\n(2) Postal Code\n(3) Address\n(4) Return");
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
				System.out.println("Please enter an end date (use YYYY-MM-DD format, or enter 'n' to leave empty)");
				String endDate = scanner.next();

				startDate = Main.setDate(startDate);
				endDate = Main.setDate(endDate);

				List<String> amenityList = new ArrayList<>();
				while (true){
					System.out.println("Please enter an amenity you would like to include (enter 'exit' to stop)");
					String amenity = scanner.next();
					if (amenity.equals("exit")){
						break;
					}
					amenityList.add(amenity);
				}

				System.out.println("Would you like to sort by (1) distance, (2) ascending price, or (3) descending price?");
				int sortOption = scanner.nextInt();
				boolean sortDist = false;
				boolean sortAscPrice = false;
				if (sortOption == 1) {
					sortDist = true;
				} else if (sortOption == 2) {
					sortAscPrice = true;
				} else if (sortOption == 3) {
					sortAscPrice = false;
				} else {
					System.out.println("Invalid option, defaulting to sorting by descending price.");
				}

				List<Dates> searchListings = QueriesController.getListingWithinDistance(longitude, latitude, distance, sortDist, sortAscPrice, startDate, endDate, minPrice, maxPrice, amenityList);

				while (true) {
					System.out.println("Listings:\n--------");
					Main.printDateList(searchListings);
					System.out.println("--------");
					System.out.println("Please enter the listingId that you would like to rent, or '0' to exit:");
					int listingId = scanner.nextInt();
					if (listingId == 0) {
						System.out.println("Exiting...");
						break;
					} else {
						Listing listing = ListingController.getListing(listingId);
						if (listing == null) {
							System.out.println("Error: Invalid listingId.");
							continue;
						}

						System.out.println("Please enter a start date for your booking (in YYYY-MM-DD format):");
						String bookingStartDate = scanner.next();
						System.out.println("Please enter an end date for your booking (in YYYY-MM-DD format):");
						String bookingEndDate = scanner.next();

						// Check availability
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						try {
							Date startDate2 = sdf.parse(bookingStartDate);
							Date endDate2 = sdf.parse(bookingEndDate);

							Calendar calendar = Calendar.getInstance();
							calendar.setTime(startDate2);

							while (!calendar.getTime().after(endDate2)) {
								String currentDateStr = sdf.format(calendar.getTime());
								String currentDate = formatDate(currentDateStr);
								Availability availability = AvailabilityController.getAvailability(listingId, currentDate);
								if (availability == null || availability.isAvailable() == false) {
									System.out.println("Sorry, this listing is not available on " + currentDate);
									return;
								}
								calendar.add(Calendar.DAY_OF_MONTH, 1);
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}

						System.out.println("Please enter your credit card number:");
						String cardNumber = scanner.next();

						Main.bookDates(bookingStartDate, bookingEndDate, listingId);

						Booking booking = new Booking(0, listingId, user.getUserId(), bookingStartDate, bookingEndDate, 0, "", cardNumber, -1);
						BookingController.addBooking(booking);
						System.out.println("Successfully booked! Your bookingId is: " + booking.getBookingId());
						break;

					}
				}

				continue;
			}
			else if (choice == 2) {
				scanner.nextLine();
				System.out.println("Please enter a postal code: ");
				String postalCode = scanner.nextLine();
				System.out.println("Please enter the minimum price (enter '0' to not set one): ");
				int minPrice = scanner.nextInt();
				System.out.println("Please enter the maximum price (enter '0' to not set one): ");
				int maxPrice = scanner.nextInt();
				System.out.println("Please enter a start date (use YYYY-MM-DD format, or enter 'n' to leave empty)");
				String startDate = scanner.next();
				System.out.println("Please enter an end date (use YYYY-MM-DD format, or enter 'n' to leave empty)");
				String endDate = scanner.next();

				startDate = Main.setDate(startDate);
				endDate = Main.setDate(endDate);

				List<String> amenityList = new ArrayList<>();
				while (true){
					System.out.println("Please enter an amenity you would like to include (enter 'exit' to stop)");
					String amenity = scanner.next();
					if (amenity.equals("exit")){
						break;
					}
					amenityList.add(amenity);
				}

				System.out.println("Would you like to sort by (1) distance, (2) ascending price, or (3) descending price?");
				int sortOption = scanner.nextInt();
				boolean sortDist = false;
				boolean sortAscPrice = false;
				if (sortOption == 1) {
					sortDist = true;
				} else if (sortOption == 2) {
					sortAscPrice = true;
				} else if (sortOption == 3) {
					sortAscPrice = false;
				} else {
					System.out.println("Invalid option, defaulting to sorting by descending price.");
				}

				List<Dates> searchListings = QueriesController.getListingByPostalCode(postalCode, sortAscPrice, startDate, endDate, minPrice, maxPrice, amenityList);

				while (true) {
					System.out.println("Listings:\n--------");
					Main.printDateList(searchListings);
					System.out.println("--------");
					System.out.println("Please enter the listingId that you would like to rent, or '0' to exit:");
					int listingId = scanner.nextInt();
					if (listingId == 0) {
						System.out.println("Exiting...");
						break;
					} else {
						Listing listing = ListingController.getListing(listingId);

						if (listing == null) {
							System.out.println("Error: Invalid listingId.");
							continue;
						}

						System.out.println("Please enter a start date for your booking (in YYYY-MM-DD format):");
						String bookingStartDate = scanner.next();
						System.out.println("Please enter an end date for your booking (in YYYY-MM-DD format):");
						String bookingEndDate = scanner.next();

						// Check availability
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						try {
							Date startDate2 = sdf.parse(bookingStartDate);
							Date endDate2 = sdf.parse(bookingEndDate);

							Calendar calendar = Calendar.getInstance();
							calendar.setTime(startDate2);

							while (!calendar.getTime().after(endDate2)) {
								String currentDateStr = sdf.format(calendar.getTime());
								String currentDate = formatDate(currentDateStr);
								Availability availability = AvailabilityController.getAvailability(listingId, currentDate);
								if (availability == null || availability.isAvailable() == false) {
									System.out.println("Sorry, this listing is not available on " + currentDate);
									return;
								}
								calendar.add(Calendar.DAY_OF_MONTH, 1);
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}

						System.out.println("Please enter your credit card number:");
						String cardNumber = scanner.next();

						Main.bookDates(bookingStartDate, bookingEndDate, listingId);

						Booking booking = new Booking(0, listingId, user.getUserId(), bookingStartDate, bookingEndDate, 0, "", cardNumber, -1);
						BookingController.addBooking(booking);
						System.out.println("Successfully booked! Your bookingId is: " + booking.getBookingId());
						break;

					}
				}

				continue;
			}
			else if (choice == 3) {
				System.out.println("Please enter an address: ");
				scanner.nextLine();
				String address = scanner.nextLine();
				System.out.println("Please enter the minimum price (enter '0' to not set one): ");
				int minPrice = scanner.nextInt();
				System.out.println("Please enter the maximum price (enter '0' to not set one): ");
				int maxPrice = scanner.nextInt();
				System.out.println("Please enter a start date (use YYYY-MM-DD format, or enter 'n' to leave empty)");
				String startDate = scanner.next();
				System.out.println("Please enter an end date (use YYYY-MM-DD format, or enter 'n' to leave empty)");
				String endDate = scanner.next();

				startDate = Main.setDate(startDate);
				endDate = Main.setDate(endDate);

				List<String> amenityList = new ArrayList<>();
				while (true){
					System.out.println("Please enter an amenity you would like to include (enter 'exit' to stop)");
					String amenity = scanner.next();
					if (amenity.equals("exit")){
						break;
					}
					amenityList.add(amenity);
				}

				System.out.println("Would you like to sort by (1) distance, (2) ascending price, or (3) descending price?");
				int sortOption = scanner.nextInt();
				boolean sortDist = false;
				boolean sortAscPrice = false;
				if (sortOption == 1) {
					sortDist = true;
				} else if (sortOption == 2) {
					sortAscPrice = true;
				} else if (sortOption == 3) {
					sortAscPrice = false;
				} else {
					System.out.println("Invalid option, defaulting to sorting by descending price.");
				}

				List<Dates> searchListings = QueriesController.getListingByAddress(address, sortAscPrice, startDate, endDate, minPrice, maxPrice, amenityList);

				while (true) {
					System.out.println("Listings:\n--------");
					Main.printDateList(searchListings);
					System.out.println("--------");
					System.out.println("Please enter the listingId that you would like to rent, or '0' to exit:");
					int listingId = scanner.nextInt();
					if (listingId == 0) {
						System.out.println("Exiting...");
						break;
					} else {
						Listing listing = ListingController.getListing(listingId);

						if (listing == null) {
							System.out.println("Error: Invalid listingId.");
							continue;
						}

						System.out.println("Please enter a start date for your booking (in YYYY-MM-DD format):");
						String bookingStartDate = scanner.next();
						System.out.println("Please enter an end date for your booking (in YYYY-MM-DD format):");
						String bookingEndDate = scanner.next();

						// Check availability
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						try {
							Date startDate2 = sdf.parse(bookingStartDate);
							Date endDate2 = sdf.parse(bookingEndDate);

							Calendar calendar = Calendar.getInstance();
							calendar.setTime(startDate2);

							while (!calendar.getTime().after(endDate2)) {
								String currentDateStr = sdf.format(calendar.getTime());
								String currentDate = formatDate(currentDateStr);
								Availability availability = AvailabilityController.getAvailability(listingId, currentDate);
								if (availability == null || availability.isAvailable() == false) {
									System.out.println("Sorry, this listing is not available on " + currentDate);
									return;
								}
								calendar.add(Calendar.DAY_OF_MONTH, 1);
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}

						System.out.println("Please enter your credit card number:");
						String cardNumber = scanner.next();

						Main.bookDates(bookingStartDate, bookingEndDate, listingId);

						Booking booking = new Booking(0, listingId, user.getUserId(), bookingStartDate, bookingEndDate, 0, "", cardNumber, -1);
						BookingController.addBooking(booking);
						System.out.println("Successfully booked! Your bookingId is: " + booking.getBookingId());
						break;

					}
				}

				continue;
			}
			else if (choice == 4) {
				System.out.println("Returning to rent menu...");
				break;
			} else {
				System.out.println("Invalid input\n");
			}
		}
	}

	public static void cancelBooking (User user, Scanner scanner) {
		System.out.println("------Cancel Booking-------");
		System.out.println("Please enter the bookingId that you would like to cancel: ");
		int bookingId = scanner.nextInt();
		Booking booking = BookingController.getBooking(bookingId);

		if (booking == null) {
			System.out.println("Error: Invalid bookingId.");
			return;
		} else if (booking.getGuestId() != user.getUserId()){
			System.out.println("Error: This is not your booking.");
			return;
		}

		// Set all availability dates to true
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date startDate = sdf.parse(booking.getStartDate());
			Date endDate = sdf.parse(booking.getEndDate());

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startDate);

			while (!calendar.getTime().after(endDate)) {
				String currentDateStr = sdf.format(calendar.getTime());
				String currentDate = formatDate(currentDateStr);
				Availability availability = AvailabilityController.getAvailability(booking.getListingId(), currentDate);
				availability.setAvailable(true);
				AvailabilityController.editAvailability(availability);
				calendar.add(Calendar.DAY_OF_MONTH, 1);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		user.setGuestCancels(user.getGuestCancels() + 1);
		UserController.editUser(user);
		booking.setCancelledBy(user.getUserId());
		BookingController.editBooking(booking);
		System.out.println("Successfully cancelled booking.");
		return;
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
				scanner.nextLine();
				System.out.println("Please enter your occupation: ");
				String occupation = scanner.nextLine();
				System.out.println("Please enter your address: ");
				String address = scanner.nextLine();
				System.out.println("Please enter your date of birth (in YYYY-MM-DD format): ");
				String dob = scanner.nextLine();
				System.out.println("Please enter your SIN (numerical only, no special characters): ");
				int sin = scanner.nextInt();
				System.out.println("Please enter your name (this will be your display name): ");
				scanner.nextLine();
				String name = scanner.nextLine();
				User newUser = new User(0, occupation, address, dob, sin, name, 0, 0);
				UserController.addUser(newUser);
				System.out.println("Registration Complete! Your UserId is: " + newUser.getUserId());
				System.out.println("Please note your UserId, as it will be required for signing in.");
			}
		}
	}

	static void printDateList(List<Dates> searchListings) {
		for (int i = 0 ; i < searchListings.size(); i++) {
			Dates listing = searchListings.get(i);
			System.out.println("ListingID: " + listing.getListingId() + " | Address: " + listing.getAddress() + " | Price: " + listing.getPrice() + " | Date: " + listing.getDate() + " | Amenities: " + listing.getAmenities() + " | Quantity: " + listing.getQuantities());
		}
	}

	static void bookDates(String bookingStartDate, String bookingEndDate, int listingId) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date startDate2 = sdf.parse(bookingStartDate);
			Date endDate2 = sdf.parse(bookingEndDate);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startDate2);

			while (!calendar.getTime().after(endDate2)) {
				String currentDateStr = sdf.format(calendar.getTime());
				String currentDate = formatDate(currentDateStr);
				Availability availability = AvailabilityController.getAvailability(listingId,
						currentDate);
				availability.setAvailable(false);
				AvailabilityController.editAvailability(availability);
				calendar.add(Calendar.DAY_OF_MONTH, 1);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	static String setDate(String date) {
		if (date.equals("n")){
			date = null;
		} else {
			date = formatDate(date);
		}
		return date;
	}
}