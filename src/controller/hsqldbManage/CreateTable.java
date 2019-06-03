package controller.hsqldbManage;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {
	public static void main(String[] args) throws SQLException {

		final String DB_NAME = "propertyDB";
		final String TABLE1_NAME = "RENTAL_PROPERTY";
		final String TABLE2_NAME = "RENTAL_RECORD";

		// use try-with-resources Statement
		// create table rental_property.
		try (Connection con = ConnectionTest.getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			int result1 = stmt.executeUpdate("CREATE TABLE RENTAL_PROPERTY (" + "id VARCHAR(40) NOT NULL,"
					+ "street_number INT NOT NULL," + "street_name VARCHAR(20) NOT NULL,"
					+ "suburb VARCHAR(20) NOT NULL," + "bedroom_number INT NOT NULL,"
					+ "property_type VARCHAR(20) NOT NULL," + "status VARCHAR(20) NOT NULL," + "url VARCHAR(100) ,"
					+ "desc VARCHAR(100) NOT NULL," + "last_maintenance_date VARCHAR(20)," + "PRIMARY KEY (id))");
			if (result1 == 0) {
				System.out.println("Table " + TABLE1_NAME + " has been created successfully");
			} else {
				System.out.println("Table " + TABLE1_NAME + " is not created");
			}
			// create table rental_record.
			int result2 = stmt.executeUpdate("CREATE TABLE RENTAL_RECORD (" + "pid VARCHAR(40) NOT NULL,"
					+ "rid VARCHAR(80) NOT NULL," + "rent_date VARCHAR(20) NOT NULL," + "est_return_date VARCHAR(20) NOT NULL,"
					+ "act_return_date VARCHAR(20) NOT NULL," + "rental_fee FLOAT NOT NULL," + "late_fee FLOAT NOT NULL,"
					+ "PRIMARY KEY (rid))");
			if (result2 == 0) {
				System.out.println("Table " + TABLE2_NAME + " has been created successfully");
			} else {
				System.out.println("Table " + TABLE2_NAME + " is not created");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
