package model;
public class rentalRecord {
	// variables
	private String recordID;
	private DateTime rentDate;
	private DateTime estimatedReturnDate;
	private DateTime actualReturnDate;
	private double rentalFee;
	private double lateFee;
	private boolean isReturn;
	private rentalProperty rP;
	private int numberOfDays;
	private String customerID;

	// constructor
	public rentalRecord(rentalProperty rP, String customerID, int numberOfDays, DateTime rentDate) {
		this.rP = rP;
		this.numberOfDays = numberOfDays;
		this.recordID = rP.getPropertyID() + "_" + customerID + "_" + rentDate.getEightDigitDate();
		this.rentDate=rentDate;
		this.estimatedReturnDate= new DateTime(rentDate,numberOfDays);
		this.isReturn=false;
		this.customerID=customerID;
	}
	public rentalRecord(rentalProperty rp,String id, DateTime rent_date, DateTime est_return_date, DateTime act_return_date, double rental_fee, double late_fee) {
		this.rP=rp;
		this.recordID=id;
		this.rentDate=rent_date;
		this.estimatedReturnDate=est_return_date;
		this.actualReturnDate=act_return_date;
		this.rentalFee=rental_fee;
		this.lateFee=late_fee;
		this.isReturn=true;
	}
	
	// getters

	public DateTime getRentDate() {
		return rentDate;
	}

	public DateTime getActualReturnDate() {
		return actualReturnDate;
	}

	public void setActualReturnDate(DateTime actualReturnDate) {
		this.actualReturnDate = actualReturnDate;
	}

	public double getRentalFee() {
		return rentalFee;
	}

	public String getCustomerID() {
		return customerID;
	}


	public void setRentalFee() {
		double rentalFee;
		int actualDay=DateTime.diffDays(this.actualReturnDate, this.rentDate);
		if(actualDay>this.numberOfDays) {
		if (this.rP instanceof apartment) {
			// 1-bedroom apartment
			if (this.rP.getNumberOfBedrooms() == 1) {
				rentalFee = 143 * this.numberOfDays;
				this.rentalFee = rentalFee;
			}
			// 2-bedroom apartment
			if (this.rP.getNumberOfBedrooms() == 2) {
				rentalFee = 210 * this.numberOfDays;
				this.rentalFee = rentalFee;
			}
			// 3-bedroom apartment
			if (this.rP.getNumberOfBedrooms() == 3) {
				rentalFee = 319 * this.numberOfDays;
				this.rentalFee = rentalFee;
			}
		} else {
			// rP is premiumSuite
			rentalFee = 554 * this.numberOfDays;
			this.rentalFee = rentalFee;
		}
		}else {
			if (this.rP instanceof apartment) {
				// 1-bedroom apartment
				if (this.rP.getNumberOfBedrooms() == 1) {
					rentalFee = 143 * actualDay;
					this.rentalFee = rentalFee;
				}
				// 2-bedroom apartment
				if (this.rP.getNumberOfBedrooms() == 2) {
					rentalFee = 210 * actualDay;
					this.rentalFee = rentalFee;
				}
				// 3-bedroom apartment
				if (this.rP.getNumberOfBedrooms() == 3) {
					rentalFee = 319 * actualDay;
					this.rentalFee = rentalFee;
				}
			} else {
				// rP is premiumSuite
				rentalFee = 554 * actualDay;
				this.rentalFee = rentalFee;
			}
		}
	}

	public double getLateFee() {
		return lateFee;
	}

	public void setLateFee() {
		double lateFee;
		int delay = DateTime.diffDays(this.actualReturnDate, this.estimatedReturnDate);
		if (this.rP instanceof apartment && delay > 0) {
			if (this.rP.getNumberOfBedrooms() == 1) {
				lateFee = delay * 143 * 1.15;
				this.lateFee = lateFee;
			} else if (this.rP.getNumberOfBedrooms() == 2) {
				lateFee = delay * 210 * 1.15;
				this.lateFee = lateFee;
			} else if (this.rP.getNumberOfBedrooms() == 3) {
				lateFee = delay * 319 * 1.15;
				this.lateFee = lateFee;
			}

		} else if (this.rP instanceof premiumSuite && delay > 0) {
			lateFee = delay * 662;
			this.lateFee = lateFee;
		} else
			this.lateFee = 0;
	}

	public boolean isReturn() {
		return isReturn;
	}

	public void setIsReturn() {
		this.isReturn = true;
	}

	public void setIsRented() {
		this.isReturn = false;
	}

	public String toString() {
		if (this.isReturn = false)
			return this.recordID + ":" + this.rentDate + ":" + this.estimatedReturnDate;
		else
			return this.recordID + ":" + this.rentDate + ":" + this.estimatedReturnDate + ":" + this.actualReturnDate
					+ ":" + this.rentalFee + ":" + this.lateFee;
	}

	public String getDetails() {
		String details;
		details = String.format("%-30s  %7s\n", "Record ID:", this.recordID)
				+ String.format("%-30s  %7s\n", "Rent Date:", this.rentDate)
				+ String.format("%-19s  %7s\n", "Estimated Return Date:", this.estimatedReturnDate);
		if (getRentalFee()==0) {
			return details;
		} else {
			details += String.format("%-24s  %7s\n", "Actual Return Date:", this.actualReturnDate)
					+ String.format("%-30s  %7.2f\n", "Rental Fee:", this.rentalFee)
					+ String.format("%-31s  %7.2f\n", "Late Fee:", this.lateFee);
			return details;

		}
	}
}
