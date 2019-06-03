package model;

import model.exception.RentException;

public class premiumSuite extends rentalProperty {

	public premiumSuite(int streetNum, String streetName, String suburb, int numberOfBedrooms, String propertyType,
			String url, String desc, DateTime maintenanceDate) {
		super(streetNum, streetName, suburb, 3, propertyType, url, desc);
		super.setPropertyID("S_" + super.getStreetNum() + "_" + super.getStreetName() + "_" + super.getSuburb());
		propertyType = "Premium Suite";
		super.setMaintenanceDate(maintenanceDate);

	}

	public void rent(String customerID, DateTime rentDate, int numOfRentDay) throws Exception {
		DateTime dt0 = new DateTime(super.getMaintenanceDate(), 10);
		rentalRecord r = new rentalRecord(this, customerID, numOfRentDay, rentDate);
		// check the interval
		int num = DateTime.diffDays(dt0, rentDate);
		if (super.getPropertyStatus().compareTo("Available") == 0) {
			if (numOfRentDay <= num) {
				super.setPropertyStatusToRented();
				super.addRecord(r);
				r.setIsRented();
			} else {
				throw new RentException("Invalid check in/out date due to conflict with maintenance date.");
			}
		} else {
			throw new RentException("The property (" + this.getPropertyID() + ") is not ready for renting.");
		}
	}
}