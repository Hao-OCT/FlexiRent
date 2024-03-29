package model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTime {

	private long advance;
	private long time;

	public DateTime() {
		time = System.currentTimeMillis();
	}

	public DateTime(int setClockForwardInDays) {
		advance = ((setClockForwardInDays * 24L + 0) * 60L) * 60000L;
		time = System.currentTimeMillis() + advance;
	}

	public DateTime(DateTime startDate, int setClockForwardInDays) {
		advance = ((setClockForwardInDays * 24L + 0) * 60L) * 60000L;
		time = startDate.getTime() + advance;
	}

	public DateTime(int day, int month, int year) {
		setDate(day, month, year);
	}

	public long getTime() {
		return time;
	}

	public String toString() {
		// long currentTime = getTime();
		// Date gct = new Date(currentTime);
		// return gct.toString();
		return getFormattedDate();
	}

	public static String getCurrentTime() {
		Date date = new Date(System.currentTimeMillis()); // returns current Date/Time
		return date.toString();
	}

	public String getFormattedDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		long currentTime = getTime();
		Date gct = new Date(currentTime);

		return sdf.format(gct);
	}

	public String getEightDigitDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		long currentTime = getTime();
		Date gct = new Date(currentTime);

		return sdf.format(gct);
	}

	// returns difference in days
	public static int diffDays(DateTime endDate, DateTime startDate) {
		final long HOURS_IN_DAY = 24L;
		final int MINUTES_IN_HOUR = 60;
		final int SECONDS_IN_MINUTES = 60;
		final int MILLISECONDS_IN_SECOND = 1000;
		long convertToDays = HOURS_IN_DAY * MINUTES_IN_HOUR * SECONDS_IN_MINUTES * MILLISECONDS_IN_SECOND;
		long hirePeriod = endDate.getTime() - startDate.getTime();
		double difference = (double) hirePeriod / (double) convertToDays;
		int round = (int) Math.round(difference);
		return round;
	}

	private void setDate(int day, int month, int year) {

		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day, 0, 0);

		java.util.Date date = calendar.getTime();

		time = date.getTime();
	}

	// Advances date/time by specified days, hours and mins for testing purposes
	public void setAdvance(int days, int hours, int mins) {
		advance = ((days * 24L + hours) * 60L) * 60000L;
	}

	public DateTime convertDate(String date) {
		String stringDay, stringMonth, stringYear;
		stringDay = date.substring(0, 2);
		stringMonth = date.substring(3, 5);
		stringYear = date.substring(6, 10);
		int day = Integer.parseInt(stringDay);
		int month = Integer.parseInt(stringMonth);
		int year = Integer.parseInt(stringYear);
		DateTime dt = new DateTime(day, month, year);
		return dt;
	}

	public int getDayOfWeek(int day, int month, int year) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, day);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

}
