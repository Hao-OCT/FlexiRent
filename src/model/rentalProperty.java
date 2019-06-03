package model;

import java.util.ArrayList;

import model.exception.CompleteException;
import model.exception.PerformException;
import model.exception.ReturnException;

public abstract class rentalProperty {
	// variables
	private String propertyID;
	private int streetNum;
	private String streetName;
	private String suburb;
	private int numberOfBedrooms;
	private String propertyType;
	private String propertyStatus;
	private ArrayList<rentalRecord> record = new ArrayList<rentalRecord>(10);
	private DateTime maintenanceDate;
	private String url;
	private String desc;

	// constructor
	public rentalProperty(int streetNum, String streetName, String suburb, int numberOfBedrooms, String propertyType,
			String url, String desc) {
		this.streetNum = streetNum;
		this.streetName = streetName;
		this.suburb = suburb;
		this.numberOfBedrooms = numberOfBedrooms;
		this.propertyType = propertyType;
		this.propertyStatus = "Available";
		this.url = url;
		this.desc = desc;
	}

	public String getPropertyID() {
		return propertyID;
	}

	public void setPropertyID(String propertyID) {
		this.propertyID = propertyID;
	}

	public int getStreetNum() {
		return streetNum;
	}

	public String getStreetName() {
		return streetName;
	}

	public String getSuburb() {
		return suburb;
	}

	public int getNumberOfBedrooms() {
		return numberOfBedrooms;
	}

	public String getPropertyStatus() {
		return propertyStatus;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public ArrayList<rentalRecord> getRecord() {
		return record;
	}

	public DateTime getMaintenanceDate() {
		return maintenanceDate;
	}

	public void setMaintenanceDate(DateTime maintenanceDate) {
		this.maintenanceDate = maintenanceDate;
	}

	public void setPropertyStatusToRented() {
		this.propertyStatus = "Rented";
	}

	public void setPropertyStatusToAvailable() {
		this.propertyStatus = "Available";
	}

	public void setPropertyStatusToMaintenance() {
		this.propertyStatus = "Maintenance";
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDesc() {
		return desc;
	}

	public void addRecord(rentalRecord r) {
		if (this.record.size() < 10) {
			record.add(0, r);
		} else {
			record.remove(9);
			record.add(0, r);
		}
	}

	public abstract void rent(String customerID, DateTime rentDate, int numOfRentDay) throws Exception;

	public void returnProperty(DateTime returnDate) throws ReturnException {
		if (DateTime.diffDays(returnDate, record.get(0).getRentDate()) < 0) {
			throw new ReturnException("Invalid check out date.");
		} else {
			record.get(0).setActualReturnDate(returnDate);
			record.get(0).setRentalFee();
			record.get(0).setLateFee();
			setPropertyStatusToAvailable();
			record.get(0).getActualReturnDate();
			record.get(0).getRentalFee();
			record.get(0).getLateFee();
			record.get(0).setIsReturn();
		}
	}

	public void performMaintenance() throws PerformException {
		if (this.propertyStatus == "Available") {
			this.setPropertyStatusToMaintenance();
		} else {
			throw new PerformException("Not ready to maintain.");
		}
	}

	public void completeMaintenance(DateTime completionDate) throws CompleteException {
		if (this.propertyStatus == "Maintenance" && this instanceof premiumSuite) {
			this.maintenanceDate = completionDate;
			this.setPropertyStatusToAvailable();
		} else if (this.propertyStatus == "Maintenance" && this instanceof apartment) {
			this.setPropertyStatusToAvailable();
		} else
			throw new CompleteException("Cannot complete maintenance.");
	}

	public String toString() {
		if (this instanceof apartment) {
			return this.propertyID + ":" + this.streetNum + ":" + this.streetName + ":" + this.suburb + ":"
					+ this.numberOfBedrooms + ":" + this.propertyType + ":" + this.propertyStatus + ":" + this.url + ":"
					+ this.desc;
		} else
			return this.propertyID + ":" + this.streetNum + ":" + this.streetName + ":" + this.suburb + ":"
					+ this.numberOfBedrooms + ":" + this.propertyType + ":" + this.propertyStatus + ":" + this.url + ":"
					+ this.desc + ":" + this.maintenanceDate;
	}

	public String getDetails() {
		String address = this.streetNum + " " + this.streetName + " " + this.suburb;
		String details = String.format("%-30s  %7s\n", "Property ID:", this.propertyID)
				+ String.format("%-31s  %7s\n", "Address:", address)
				+ String.format("%-34s  %7s\n", "Type:", this.propertyType)
				+ String.format("%-24s  %7d\n", "Bedroom:", this.numberOfBedrooms)
				+ String.format("%-33s  %7s\n", "Status:", this.propertyStatus);
		//String line = "----------------------------------------------------------\n";
		if (this instanceof premiumSuite) {
			details += String.format("%-24s  %7s\n", "Last maintenance:", this.maintenanceDate);
		}
		return details;
//		if (this.getPropertyStatus().compareTo("Rented") != 0) {
//			if (record.size() == 0) {
//				details += String.format("%-20s  %7s\n", "RENTAL RECORD:", "empty");
//				return details;
//			} else {
//				details += String.format("%-30s\n", "RENTAL RECORD");
//				for (int i = 0; i < record.size(); i++) {
//					details += line + record.get(i).getDetails();
//				}
//				return details;
//			}
//		} else {
//			if (record.size() == 0) {
//				details += String.format("%-30s\n", "RENTAL RECORD") + record.get(0).getDetails();
//				return details;
//			} else {
//				details += String.format("%-30s\n", "RENTAL RECORD") + record.get(0).getDetails();
//				for (int i = 1; i < record.size(); i++) {
//					details += line + record.get(i).getDetails();
//				}
//				return details;
//			}
//		}
	}
}