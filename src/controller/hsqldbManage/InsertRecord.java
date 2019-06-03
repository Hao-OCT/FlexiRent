package controller.hsqldbManage;

import java.sql.Connection;
import java.sql.Statement;

public class InsertRecord {
	public static void main(String[] args) {
		final String DB_NAME = "propertyDB";
		final String TABLE2_NAME = "RENTAL_RECORD";
		// use try-with-resources Statement
		try (Connection con = ConnectionTest.getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			String query = "INSERT INTO " + TABLE2_NAME
					+ " VALUES ('A_68_Hayward La_Melbourne','A_68_Hayward La_Melbourne_CID001_01092018','01/09/2018','04/09/2018','04/09/2018',429.00,0.00)"
					+ "INSERT INTO " + TABLE2_NAME
					+ " VALUES ('A_68_Hayward La_Melbourne','A_68_Hayward La_Melbourne','05/09/2018','09/09/2018','10/09/2018',572.00,164.45)"
					+ "INSERT INTO " + TABLE2_NAME
					+ " VALUES ('A_48_Blenheim_Balaclava','A_48_Blenheim_Balaclava_CID001_01092018','01/09/2018','04/09/2018','04/09/2018',429.00,0.00)"
					+ "INSERT INTO " + TABLE2_NAME
					+ " VALUES ('A_48_Blenheim_Balaclava','A_48_Blenheim_Balaclava_CID002_05092018','05/09/2018','09/09/2018','10/09/2018',572.00,164.45)"
					+ "INSERT INTO " + TABLE2_NAME
					+ " VALUES ('A_18_Cochrane St_Brighton','A_18_Cochrane St_Brighton_CID001_01092018','01/09/2018','04/09/2018','04/09/2018',429.00,0.00)"
					+ "INSERT INTO " + TABLE2_NAME
					+ " VALUES ('A_18_Cochrane St_Brighton','A_18_Cochrane St_Brighton_CID002_05092018','05/09/2018','09/09/2018','10/09/2018',572.00,164.45)"
					+ "INSERT INTO " + TABLE2_NAME
					+ " VALUES ('A_300_Swanston St_Melbourne','A_300_Swanston St_Melbourne_CID001_01092018','01/09/2018','04/09/2018','04/09/2018',429.00,0.00)"
					+ "INSERT INTO " + TABLE2_NAME
					+ " VALUES ('A_300_Swanston St_Melbourne','A_300_Swanston St_Melbourne_CID002_05092018','05/09/2018','09/09/2018','10/09/2018',572.00,164.45)"
					+ "INSERT INTO " + TABLE2_NAME
					+ " VALUES ('A_36_La Trobe_Melbourne','A_36_La Trobe_Melbourne_CID001_11092018','11/09/2018','14/09/2018','14/09/2018',630.00,0.00)"
					+ "INSERT INTO " + TABLE2_NAME
					+ " VALUES ('A_36_La Trobe_Melbourne','A_36_La Trobe_Melbourne_CID002_15092018','15/09/2018','19/09/2018','20/09/2018',630.00,483.00)"
					+ "INSERT INTO " + TABLE2_NAME
					+ " VALUES ('A_6_Te-Arai Av_St Kilda East','A_6_Te-Arai Av_St Kilda East_CID001_11092018','11/09/2018','14/09/2018','14/09/2018',630.00,0.00)"
					+ "INSERT INTO " + TABLE2_NAME
					+ " VALUES ('A_6_Te-Arai Av_St Kilda East','A_6_Te-Arai Av_St Kilda East_CID002_15092018','15/09/2018','19/09/2018','20/09/2018',630.00,483.00)"
					+ "INSERT INTO " + TABLE2_NAME
					+ " VALUES ('A_668_Bourke St_Melbourne','A_668_Bourke St_Melbourne_CID001_11092018','11/09/2018','14/09/2018','14/09/2018',630.00,0.00)"
					+ "INSERT INTO " + TABLE2_NAME
					+ " VALUES ('A_668_Bourke St_Melbourne','A_668_Bourke St_Melbourne_CID002_15092018','15/09/2018','19/09/2018','20/09/2018',630.00,483.00)"
					+ "INSERT INTO " + TABLE2_NAME
					+ " VALUES ('A_2_Golding St_Hawthorn','A_2_Golding St_Hawthorn_CID001_21092018','21/09/2018','24/09/2018','24/09/2018',957.00,0.00)"
					+ "INSERT INTO " + TABLE2_NAME
					+ " VALUES ('A_2_Golding St_Hawthorn','A_2_Golding St_Hawthorn_CID002_25092018','25/09/2018','28/09/2018','29/09/2018',957.00,366.85)"
					+ "INSERT INTO " + TABLE2_NAME
					+ " VALUES ('A_138_Glen Eira_Elsternwick','A_138_Glen Eira_Elsternwick_CID001_21092018','21/09/2018','24/09/2018','24/09/2018',957.00,0.00)"
					+ "INSERT INTO " + TABLE2_NAME
					+ " VALUES ('A_138_Glen Eira_Elsternwick','A_138_Glen Eira_Elsternwick_CID002_25092018','25/09/2018','28/09/2018','29/09/2018',957.00,366.85)"
					+ "INSERT INTO " + TABLE2_NAME
					+ " VALUES ('A_9_City Road_Melbourne','A_9_City Road_Melbourne_CID001_21092018','21/09/2018','24/09/2018','24/09/2018',957.00,0.00)"
					+ "INSERT INTO " + TABLE2_NAME
					+ " VALUES ('A_9_City Road_Melbourne','A_9_City Road_Melbourne_CID002_25092018','25/09/2018','28/09/2018','29/09/2018',957.00,366.85)"
					+ "INSERT INTO " + TABLE2_NAME
					+ " VALUES ('S_100_Lygon St_Melbourne','S_100_Lygon St_Melbourne_CID001_01092018','01/09/2018','03/09/2018','03/09/2018',1108.00,0.00)"
					+ "INSERT INTO " + TABLE2_NAME
					+ " VALUES ('S_100_Lygon St_Melbourne','S_100_Lygon St_Melbourne_CID002_04092018','04/09/2018','06/09/2018','07/09/2018',1109.00,662.00)"
					+ "INSERT INTO " + TABLE2_NAME
					+ " VALUES ('S_327_Lonsdale St_Melbourne','S_327_Lonsdale St_Melbourne_CID001_01092018','01/09/2018','03/09/2018','03/09/2018',1108.00,0.00)"
					+ "INSERT INTO " + TABLE2_NAME
					+ " VALUES ('S_327_Lonsdale St_Melbourne','S_327_Lonsdale St_Melbourne_CID002_04092018','04/09/2018','06/09/2018','07/09/2018',1109.00,662.00)"
					+ "INSERT INTO " + TABLE2_NAME
					+ " VALUES ('S_118_Beckett St_Melbourne','S_118_Beckett St_Melbourne_CID001_01092018','01/09/2018','03/09/2018','03/09/2018',1108.00,0.00)"
					+ "INSERT INTO " + TABLE2_NAME
					+ " VALUES ('S_118_Beckett St_Melbourne','S_118_Beckett St_Melbourne_CID002_04092018','04/09/2018','06/09/2018','07/09/2018',1109.00,662.00)"
					+ "INSERT INTO " + TABLE2_NAME
					+ " VALUES ('S_43_Lygon St_Melbourne','S_43_Lygon St_Melbourne_CID001_01092018','01/09/2018','03/09/2018','03/09/2018',1108.00,0.00)"
					+ "INSERT INTO " + TABLE2_NAME
					+ " VALUES ('S_43_Lygon St_Melbourne','S_43_Lygon St_Melbourne_CID002_04092018','04/09/2018','06/09/2018','07/09/2018',1109.00,662.00)"
					+ "INSERT INTO " + TABLE2_NAME
					+ " VALUES ('S_38_Rose La_Melbourne','S_38_Rose La_Melbourne_CID001_01092018','01/09/2018','03/09/2018','03/09/2018',1108.00,0.00)"
					+ "INSERT INTO " + TABLE2_NAME
					+ " VALUES ('S_38_Rose La_Melbourne','S_38_Rose La_Melbourne_CID002_04092018','04/09/2018','06/09/2018','07/09/2018',1109.00,662.00)";

			int result = stmt.executeUpdate(query);
			con.commit();

			System.out.println("Insert into table " + TABLE2_NAME + " executed successfully");
			System.out.println(result + " row(s) affected");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
