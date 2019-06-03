package controller.hsqldbManage;

import java.sql.Connection;
import java.sql.Statement;

public class InsertRow {
	public static void main(String[] args) {
		final String DB_NAME = "propertyDB";
		final String TABLE1_NAME = "RENTAL_PROPERTY";

		// use try-with-resources Statement
		try (Connection con = ConnectionTest.getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			String query = "INSERT INTO " + TABLE1_NAME
					+ " VALUES ('A_68_Hayward La_Melbourne', 68, 'Hayward La', 'Melbourne', 1, 'Apartment','Available','image/1.jpg','This apartment offers amazing views across Melbourne CBD.',null)"
					+ "INSERT INTO " + TABLE1_NAME
					+ " VALUES ('A_36_La Trobe_Melbourne', 36, 'La Trobe', 'Melbourne', 2, 'Apartment', 'Available','image/2.jpg','This inner city pad is right in the middle of it all.',null)"
					+ "INSERT INTO " + TABLE1_NAME
					+ " VALUES ('A_2_Golding St_Hawthorn', 2, 'Golding St', 'Hawthorn', 3, 'Apartment','Available',null,'Comprising open plan living with designer fit out, 3 bedrooms with robes.',null)"
					+ "INSERT INTO " + TABLE1_NAME
					+ " VALUES ('A_48_Blenheim_Balaclava', 48, 'Blenheim', 'Balaclava', 1, 'Apartment', 'Available','image/4.jpg','Features include security entrance, intercom system, air conditioning.',null)"
					+ "INSERT INTO " + TABLE1_NAME
					+ " VALUES ('A_6_Te-Arai Av_St Kilda East', 6, 'Te-Arai Av', 'St Kilda East', 2, 'Apartment', 'Available','image/5.jpg','Walk to your favourite shops in the CBD, many Universities, QV.',null)"
					+ "INSERT INTO " + TABLE1_NAME
					+ " VALUES ('A_138_Glen Eira_Elsternwick', 138, 'Glen Eira', 'Elsternwick', 3, 'Apartment', 'Available','image/6.jpg','This stunning apartment has been to service every need of inner CBD living.',null)"
					+ "INSERT INTO " + TABLE1_NAME
					+ " VALUES ('A_18_Cochrane St_Brighton', 18, 'Cochrane St', 'Brighton', 1, 'Apartment', 'Available','image/7.jpg','The kitchen is state of the art with stone benchtops.',null)"
					+ "INSERT INTO " + TABLE1_NAME
					+ " VALUES ('A_668_Bourke St_Melbourne', 668, 'Bourke St', 'Melbourne', 2, 'Apartment', 'Available','image/8.jpg','The bedroom is filled with an abundance of natural light.',null)"
					+ "INSERT INTO " + TABLE1_NAME
					+ " VALUES ('A_9_City Road_Melbourne', 9, 'City Road', 'Melbourne', 3, 'Apartment', 'Available','image/9.jpg','Life at City Tower provides access Communal facilities.',null)"
					+ "INSERT INTO " + TABLE1_NAME
					+ " VALUES ('A_300_Swanston St_Melbourne', 300, 'Swanston St', 'Melbourne', 1, 'Apartment', 'Available','image/10.jpg','This immaculate 5th floor apartment is great.',null)"
					+ "INSERT INTO " + TABLE1_NAME
					+ " VALUES ('S_100_Lygon St_Melbourne', 100, 'Lygon St', 'Melbourne', 3, 'Premium Suite', 'Available','image/11.jpg','Some of the local attractions include the close proximity to Lygon Street trams.','15/10/2018')"
					+ "INSERT INTO " + TABLE1_NAME
					+ " VALUES ('S_327_Lonsdale St_Melbourne', 327, 'Lonsdale St', 'Melbourne', 3, 'Premium Suite', 'Available','image/12.jpeg','The building also features a resident building manager with other security features.','15/10/2018')"
					+ "INSERT INTO " + TABLE1_NAME
					+ " VALUES ('S_118_Beckett St_Melbourne', 118, 'Beckett St', 'Melbourne', 3, 'Premium Suite', 'Available','image/13.jpg','Residents of Verve Premium Suite can relax in the 25m heated.','15/10/2018')"
					+ "INSERT INTO " + TABLE1_NAME
					+ " VALUES ('S_43_Lygon St_Melbourne', 43, 'Lygon St', 'Melbourne', 3, 'Premium Suite', 'Available','image/14.jpg','Residents of Verve Premium Suite can relax in the spa.','15/10/2018')"
					+ "INSERT INTO " + TABLE1_NAME
					+ " VALUES ('S_38_Rose La_Melbourne', 38, 'Rose La', 'Melbourne', 3, 'Premium Suite', 'Available','image/15.jpg','Accommodation features open plan living/dining area with floor to ceiling windows.','15/10/2018')";

			int result = stmt.executeUpdate(query);

			con.commit();

			System.out.println("Insert into table " + TABLE1_NAME + " executed successfully");
			System.out.println(result + " row(s) affected");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
