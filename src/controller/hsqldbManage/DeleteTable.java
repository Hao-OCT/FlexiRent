package controller.hsqldbManage;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteTable {
	public static void main(String[] args) throws SQLException {

		final String DB_NAME = "propertyDB";
		final String TABLE1_NAME = "RENTAL_PROPERTY";
		final String TABLE2_NAME = "RENTAL_RECORD";

		// use try-with-resources Statement
		// delete table rental_property
		try (Connection con = ConnectionTest.getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			int result1 = stmt.executeUpdate("DROP TABLE RENTAL_PROPERTY");

			if (result1 == 0) {
				System.out.println("Table " + TABLE1_NAME + " has been deleted successfully");
			} else {
				System.out.println("Table " + TABLE1_NAME + " was not deleted");
			}
			int result2 = stmt.executeUpdate("DROP TABLE RENTAL_RECORD");
			// delete table rental_record
			if (result2 == 0) {
				System.out.println("Table " + TABLE2_NAME + " has been deleted successfully");
			} else {
				System.out.println("Table " + TABLE2_NAME + " was not deleted");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
}