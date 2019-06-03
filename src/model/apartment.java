package model;

import model.exception.RentException;

public class apartment extends rentalProperty {
	public apartment(int streetNum, String streetName, String suburb, int numberOfBedrooms, String propertyType,
			String url, String desc) {
		super(streetNum, streetName, suburb, numberOfBedrooms, propertyType, url, desc);
		super.setPropertyID("A_" + super.getStreetNum() + "_" + super.getStreetName() + "_" + super.getSuburb());
		propertyType = "Apartment";

	}

	public void rent(String customerID, DateTime rentDate, int numOfRentDay) throws RentException {
		rentalRecord r = new rentalRecord(this, customerID, numOfRentDay, rentDate);
		// convert DateTime to Integer
		String eightDigital = rentDate.getEightDigitDate();
		String stringDay, stringMonth, stringYear;
		stringDay = eightDigital.substring(0, 2);
		stringMonth = eightDigital.substring(2, 4);
		stringYear = eightDigital.substring(4, 8);
		int day = Integer.parseInt(stringDay);
		int month = Integer.parseInt(stringMonth);
		int year = Integer.parseInt(stringYear);
			if (super.getPropertyStatus().compareTo("Available") == 0) {// 1 represent Sunday, 2 represent Monday...7
																		// represent Saturday
				if ((rentDate.getDayOfWeek(day, month, year) <= 5) && (numOfRentDay >= 2 && numOfRentDay <= 28)) {
					super.setPropertyStatusToRented();
					super.addRecord(r);
					r.setIsRented();
				} else if ((rentDate.getDayOfWeek(day, month, year) >= 6)
						&& (numOfRentDay >= 3 && numOfRentDay <= 28)) {
					super.setPropertyStatusToRented();
					super.addRecord(r);
					r.setIsRented();
				} else {
					throw new RentException("Invalid check in/out date");
				}
			} else {
				throw new RentException("The property (" + this.getPropertyID() + ") is not ready for renting.");
			}
	}
}