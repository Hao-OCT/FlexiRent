package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.StringTokenizer;

import controller.hsqldbManage.ConnectionTest;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import model.DateTime;
import model.apartment;
import model.premiumSuite;
import model.rentalProperty;
import model.rentalRecord;
import view.OverviewUnit;
import view.DetailsWindow;

public class MainController implements Initializable {
	private String importPID;
	private OverviewUnit ou;
	private DetailsWindow dw;
	private ObservableList<String> typeList = FXCollections.observableArrayList("Apartment", "Premium Suite");
	private ObservableList<Integer> roomsList = FXCollections.observableArrayList(1, 2, 3);
	private ObservableList<String> statusList = FXCollections.observableArrayList("Available", "Rented", "Maintenance");
	private ObservableList<String> suburbList;
	public static ArrayList<rentalProperty> propertyList = new ArrayList<rentalProperty>();
	final String DB_NAME = "propertyDB";
	final String TABLE1_NAME = "RENTAL_PROPERTY";
	final String TABLE2_NAME = "RENTAL_RECORD";
	@FXML
	private FlowPane fp;
	@FXML
	private Pane pane;
	@FXML
	private MenuBar menubar;
	@FXML
	private ComboBox<String> cbType;
	@FXML
	private ComboBox<Integer> cbRooms;
	@FXML
	private ComboBox<String> cbStatus;
	@FXML
	private ComboBox<String> cbSuburb;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// READ property data FROM DATABASE and save to memory
		if (MainController.propertyList.isEmpty()) {
			try (Connection con = ConnectionTest.getConnection(DB_NAME); Statement stmt = con.createStatement();) {
				String query = "SELECT * FROM " + TABLE1_NAME + " ORDER BY id";
				try (ResultSet resultSet = stmt.executeQuery(query)) {
					while (resultSet.next()) {
						String s1 = resultSet.getString("id");
						int i1 = resultSet.getInt("Street_number");
						String s2 = resultSet.getString("street_name");
						String s3 = resultSet.getString("suburb");
						int i2 = resultSet.getInt("bedroom_number");
						String s4 = resultSet.getString("property_type");
						String s6 = resultSet.getString("url");
						String s7 = resultSet.getString("desc");
						String s8 = resultSet.getString("last_maintenance_date");
						if (s1.substring(0, 1).compareTo("A") == 0) {
							MainController.propertyList.add(new apartment(i1, s2, s3, i2, s4, s6, s7));
						} else {
							DateTime dt = new DateTime();
							MainController.propertyList
									.add(new premiumSuite(i1, s2, s3, i2, s4, s6, s7, dt.convertDate(s8)));
						}
					}
				} catch (SQLException e) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle(e.getClass().getSimpleName());
					alert.setHeaderText("Error!");
					alert.setContentText(e.getMessage());
					alert.showAndWait();
				}
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle(e.getClass().getSimpleName());
				alert.setHeaderText("Error!");
				alert.setContentText(e.getMessage());
				alert.showAndWait();
			}
		}
		// read record data and save to memory
		if (propertyList.get(0).getRecord().isEmpty()) {
			try (Connection con = ConnectionTest.getConnection(DB_NAME); Statement stmt = con.createStatement();) {
				int index = -1;
				String s1, s2, s3, s4, s5;
				double s6 = 0, s7 = 0;
				DateTime dt = new DateTime();
				String query = "SELECT * FROM " + TABLE2_NAME + " ORDER BY rid DESC";
				try (ResultSet resultSet = stmt.executeQuery(query)) {
					while (resultSet.next()) {
						s1 = resultSet.getString("pid");
						s2 = resultSet.getString("rid");
						s3 = resultSet.getString("rent_date");
						s4 = resultSet.getString("est_return_date");
						s5 = resultSet.getString("act_return_date");
						s6 = resultSet.getDouble("rental_fee");
						s7 = resultSet.getDouble("late_fee");
						for (int i = 0; i < propertyList.size(); i++) {
							if (propertyList.get(i).getPropertyID().compareTo(s1) == 0) {
								index = i;
								break;
							}
						}
						rentalRecord r = new rentalRecord(propertyList.get(index), s2, dt.convertDate(s3),
								dt.convertDate(s4), dt.convertDate(s5), s6, s7);
						if(r.getRentalFee()!=0) {
							r.setIsReturn();
						}else {
							r.setIsRented();
						}
						propertyList.get(index).getRecord().add(r);
						
					}
				} catch (SQLException e) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle(e.getClass().getSimpleName());
					alert.setHeaderText("Error!");
					alert.setContentText(e.getMessage());
					alert.showAndWait();
				}
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle(e.getClass().getSimpleName());
				alert.setHeaderText("Error!");
				alert.setContentText(e.getMessage());
				alert.showAndWait();
			}
		}
		// set ImageView and Label of each unit and make them can be clicked.
		ou = new OverviewUnit();
		dw = new DetailsWindow();
		for (int i = 0; i < propertyList.size(); i++) {
			fp.getChildren().add(ou.setUnit(propertyList.get(i)));
			int index = i;
			// Label Info can be clicked.
			ou.getInfo().setOnMouseClicked(e -> {
				pane.getChildren().removeAll(pane.getChildren());
				fp.getChildren().removeAll(fp.getChildren());
				pane.getChildren().add(dw.setDetailFunction(propertyList.get(index)));
				fp.getChildren().add(dw.setDetailUnit(propertyList.get(index)));
			});
			// ImageView can be clicked.
			ou.getImg().setOnMouseClicked(e -> {
				pane.getChildren().removeAll(pane.getChildren());
				fp.getChildren().removeAll(fp.getChildren());
				pane.getChildren().add(dw.setDetailFunction(propertyList.get(index)));
				fp.getChildren().add(dw.setDetailUnit(propertyList.get(index)));
			});
		}

		// read data from database to initialize suburb
		suburbList = FXCollections.observableArrayList();
		if (suburbList.isEmpty()) {
			try (Connection con = ConnectionTest.getConnection(DB_NAME); Statement stmt = con.createStatement();) {
				String query = "SELECT DISTINCT suburb FROM " + TABLE1_NAME;
				try (ResultSet resultSet = stmt.executeQuery(query)) {
					while (resultSet.next()) {
						String s = resultSet.getString("suburb");
						suburbList.add(s);
					}
				} catch (SQLException e) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle(e.getClass().getSimpleName());
					alert.setHeaderText("Error!");
					alert.setContentText(e.getMessage());
					alert.showAndWait();
				}
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle(e.getClass().getSimpleName());
				alert.setHeaderText("Error!");
				alert.setContentText(e.getMessage());
				alert.showAndWait();
			}
		}

		// initialize combo box
		cbType.setValue(null);
		cbType.setItems(typeList);
		cbRooms.setValue(null);
		cbRooms.setItems(roomsList);
		cbStatus.setValue(null);
		cbStatus.setItems(statusList);
		cbSuburb.setValue(null);
		cbSuburb.setItems(suburbList);
	}

	// close the application.
	public void Close(ActionEvent event) {
		menuSave(event);
		Platform.exit();
		System.exit(0);
	}

	public void goSearch(ActionEvent event) {
		menuSave(event);
		String type;
		int rooms;
		String status;
		String suburb;
		ou = new OverviewUnit();
		if (this.cbType.getValue() != null) {
			type = this.cbType.getValue();
		} else
			type = null;
		if (this.cbRooms.getValue() != null) {
			rooms = Integer.valueOf(this.cbRooms.getValue());
		} else
			rooms = 0;
		if (this.cbStatus.getValue() != null) {
			status = this.cbStatus.getValue();
		} else
			status = null;
		if (this.cbSuburb.getValue() != null) {
			suburb = this.cbSuburb.getValue();
		} else
			suburb = null;
		try (Connection con = ConnectionTest.getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			String query = "SELECT * FROM " + TABLE1_NAME;
			boolean isSecondLine = true;
			if (type != null) {
				if (isSecondLine) {
					query += " WHERE ";
					isSecondLine = false;
				} else {
					query += " AND ";
				}
				query += "property_type LIKE '" + type + "'";
			}
			if (rooms != 0) {
				if (isSecondLine) {
					query += " WHERE ";
					isSecondLine = false;
				} else {
					query += " AND ";
				}
				query += "bedroom_number = " + rooms;
			}
			if (status != null) {
				if (isSecondLine) {
					query += " WHERE ";
					isSecondLine = false;
				} else {
					query += " AND ";
				}
				query += "status LIKE '" + status + "'";
			}
			if (suburb != null) {
				if (isSecondLine) {
					query += " WHERE ";
					isSecondLine = false;
				} else {
					query += " AND ";
				}
				query += "suburb LIKE '" + suburb + "'";
			}
			try (ResultSet resultSet = stmt.executeQuery(query)) {
				fp.getChildren().removeAll(fp.getChildren());
				while (resultSet.next()) {
					String s1 = resultSet.getString("id");
					int index = -1;
					for (int i = 0; i < MainController.propertyList.size(); i++) {
						if (s1.compareTo(MainController.propertyList.get(i).getPropertyID()) == 0) {
							index = i;
							break;
						}
					}	@SuppressWarnings("deprecation")
					final Integer innerIndex = new Integer(index);			
						fp.getChildren().add(ou.setUnit(MainController.propertyList.get(index)));
						
						// Label Info can be clicked.
						ou.getInfo().setOnMouseClicked(e->{
							pane.getChildren().removeAll(pane.getChildren());
							fp.getChildren().removeAll(fp.getChildren());
							pane.getChildren().add(dw.setDetailFunction(MainController.propertyList.get(innerIndex)));
							fp.getChildren().add(dw.setDetailUnit(MainController.propertyList.get(innerIndex)));
						});
						// ImageView can be clicked.
						ou.getImg().setOnMouseClicked(e -> {
							pane.getChildren().removeAll(pane.getChildren());
							fp.getChildren().removeAll(fp.getChildren());
							pane.getChildren().add(dw.setDetailFunction(propertyList.get(innerIndex)));
							fp.getChildren().add(dw.setDetailUnit(propertyList.get(innerIndex)));
						});
				}
			} catch (SQLException e) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle(e.getClass().getSimpleName());
				alert.setHeaderText("Error!");
				alert.setContentText(e.getMessage());
				alert.showAndWait();
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(e.getClass().getSimpleName());
			alert.setHeaderText("Error!");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}

	public void dataImport(ActionEvent event) {
		// drop and create table first
		try (Connection con = ConnectionTest.getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			int result1 = stmt.executeUpdate("DROP TABLE RENTAL_PROPERTY");
			if (result1 == 0) {
			} else {
				throw new Exception("Table " + TABLE1_NAME + " was not deleted");
			}
			int result2 = stmt.executeUpdate("DROP TABLE RENTAL_RECORD");
			// delete table rental_record
			if (result2 == 0) {
			} else {
				throw new Exception("Table " + TABLE2_NAME + " was not deleted");
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(e.getClass().getSimpleName());
			alert.setHeaderText("Error!");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
		try (Connection con = ConnectionTest.getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			int result1 = stmt.executeUpdate("CREATE TABLE RENTAL_PROPERTY (" + "id VARCHAR(40) NOT NULL,"
					+ "street_number INT NOT NULL," + "street_name VARCHAR(20) NOT NULL,"
					+ "suburb VARCHAR(20) NOT NULL," + "bedroom_number INT NOT NULL,"
					+ "property_type VARCHAR(20) NOT NULL," + "status VARCHAR(20) NOT NULL," + "url VARCHAR(100) ,"
					+ "desc VARCHAR(100) NOT NULL," + "last_maintenance_date VARCHAR(20)," + "PRIMARY KEY (id))");
			if (result1 == 0) {
				// System.out.println("Table " + TABLE1_NAME + " has been created
				// successfully");
			} else {
				throw new Exception("Table " + TABLE1_NAME + " is not created");
			}
			// create table rental_record.
			int result2 = stmt.executeUpdate("CREATE TABLE RENTAL_RECORD (" + "pid VARCHAR(40) NOT NULL,"
					+ "rid VARCHAR(80) NOT NULL," + "rent_date VARCHAR(20) NOT NULL,"
					+ "est_return_date VARCHAR(20) NOT NULL," + "act_return_date VARCHAR(20) NOT NULL,"
					+ "rental_fee FLOAT NOT NULL," + "late_fee FLOAT NOT NULL," + "PRIMARY KEY (rid))");
			if (result2 == 0) {
			} else {
				throw new Exception("Table " + TABLE2_NAME + " is not created");
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(e.getClass().getSimpleName());
			alert.setHeaderText("Error!");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
		// read data
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new ExtensionFilter("txt files", "*.txt"));
		File f = fc.showOpenDialog(null);
		if (f != null) {
		} else {
			// in case of no file was chosen.
			return;
		}
		File file1 = new File(f.getAbsolutePath());
		try (Scanner input = new Scanner(file1)) {
			while (input.hasNextLine()) {
				@SuppressWarnings("unused")
				int result=0;
				try (Connection con = ConnectionTest.getConnection(DB_NAME); Statement stmt = con.createStatement();) {
					StringTokenizer stoken = new StringTokenizer(input.nextLine(), ":");
					String query;
					int k = 0;
					if (stoken.countTokens() > 6) {
						// read the property line
						importPID = stoken.nextToken();
						query = "INSERT INTO " + TABLE1_NAME + " VALUES (" + "'" + importPID + "',";
						while (stoken.hasMoreTokens()) {
							if (k == 0 || k == 3) {
								query += stoken.nextToken() + ",";
								k++;
							} else {
								query += "'" + stoken.nextToken() + "',";
								k++;
							}
						}
						if (k == 8) {
							query += "null)";
						} else {
							query = query.substring(0, query.length() - 1);
							query += ")";
						}
					} else {
						// read the record line
						query = "INSERT INTO " + TABLE2_NAME + " VALUES (" + "'" + importPID + "',";
						while (stoken.hasMoreTokens()) {
							if (k == 5 || k == 4) {
								query += stoken.nextToken() + ",";
								k++;
							} else {
								query += "'" + stoken.nextToken() + "',";
								k++;
							}
						}
						query = query.substring(0, query.length() - 1);
						query += ")";
					}
					result = stmt.executeUpdate(query);
					con.commit();
				} catch (Exception e) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle(e.getClass().getSimpleName());
					alert.setHeaderText("Error!");
					alert.setContentText(e.getMessage());
					alert.showAndWait();
				}
			}
		} catch (FileNotFoundException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(e.getClass().getSimpleName());
			alert.setHeaderText("Error!");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}

	public void dataExport(ActionEvent event) {
		menuSave(event);
		String path;
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File selectedDirectory = directoryChooser.showDialog(null);
		if (selectedDirectory == null) {
			// No Directory selected
			path = "export_data.txt";
		} else {
			path = selectedDirectory.getAbsolutePath() + "/export_data.txt";
		}
		File file1 = new File(path);
		try {
			PrintWriter output = new PrintWriter(file1);
			for (int i = 0; i < propertyList.size(); i++) {
				output.write(propertyList.get(i).toString() + "\n");
				for (int j = 0; j < propertyList.get(i).getRecord().size(); j++) {
					output.write(propertyList.get(i).getRecord().get(j).toString() + "\n");
				}
			}
			output.close(); // don't forget this method
		} catch (FileNotFoundException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(e.getClass().getSimpleName());
			alert.setHeaderText("Error!");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}

	public void homePage(ActionEvent event) {
		try {
			((Node) menubar).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
			Scene scene = new Scene(root, 1000, 800);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(e.getClass().getSimpleName());
			alert.setHeaderText("Error!");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}

	public void menuSave(ActionEvent event) {
		// drop first
		try (Connection con = ConnectionTest.getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			int result1 = stmt.executeUpdate("DROP TABLE RENTAL_PROPERTY");

			if (result1 == 0) {
			} else {
				throw new Exception("Table " + TABLE1_NAME + " was not deleted");
			}
			int result2 = stmt.executeUpdate("DROP TABLE RENTAL_RECORD");
			// delete table rental_record
			if (result2 == 0) {
			} else {
				throw new Exception("Table " + TABLE2_NAME + " was not deleted");
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(e.getClass().getSimpleName());
			alert.setHeaderText("Error!");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
		// create table
		try (Connection con = ConnectionTest.getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			int result1 = stmt.executeUpdate("CREATE TABLE RENTAL_PROPERTY (" + "id VARCHAR(40) NOT NULL,"
					+ "street_number INT NOT NULL," + "street_name VARCHAR(20) NOT NULL,"
					+ "suburb VARCHAR(20) NOT NULL," + "bedroom_number INT NOT NULL,"
					+ "property_type VARCHAR(20) NOT NULL," + "status VARCHAR(20) NOT NULL," + "url VARCHAR(100) ,"
					+ "desc VARCHAR(100) NOT NULL," + "last_maintenance_date VARCHAR(20)," + "PRIMARY KEY (id))");
			if (result1 == 0) {
			} else {
				throw new Exception("Table " + TABLE1_NAME + " is not created");
			}
			// create table rental_record.
			int result2 = stmt.executeUpdate("CREATE TABLE RENTAL_RECORD (" + "pid VARCHAR(40) NOT NULL,"
					+ "rid VARCHAR(80) NOT NULL," + "rent_date VARCHAR(20) NOT NULL,"
					+ "est_return_date VARCHAR(20) NOT NULL," + "act_return_date VARCHAR(20) NOT NULL,"
					+ "rental_fee FLOAT NOT NULL," + "late_fee FLOAT NOT NULL," + "PRIMARY KEY (rid))");
			if (result2 == 0) {
			} else {
				throw new Exception("Table " + TABLE2_NAME + " is not created");
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(e.getClass().getSimpleName());
			alert.setHeaderText("Error!");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
		// insert property rows
		try (Connection con = ConnectionTest.getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			@SuppressWarnings("unused")
			int result = 0;
			for (int i = 0; i < propertyList.size(); i++) {
				StringTokenizer stoken = new StringTokenizer(propertyList.get(i).toString(), ":");
				String query = "INSERT INTO " + TABLE1_NAME + " VALUES (";
				int k = 0;
				while (stoken.hasMoreTokens()) {
					if (k == 1 || k == 4) {
						query += stoken.nextToken() + ",";
						k++;
					} else {
						query += "'" + stoken.nextToken() + "',";
						k++;
					}
				}
				if (k == 9) {
					query += "null)";
				} else {
					query = query.substring(0, query.length() - 1);
					query += ")";
				}
				result = stmt.executeUpdate(query);
			}
			con.commit();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(e.getClass().getSimpleName());
			alert.setHeaderText("Error!");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
		// insert record rows
		try (Connection con = ConnectionTest.getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			@SuppressWarnings("unused")
			int result = 0;
			// divide toString into parts by using StringTokenizer
			for (int i = 0; i < propertyList.size(); i++) {
				for (int j = 0; j < propertyList.get(i).getRecord().size(); j++) {
					StringTokenizer stoken = new StringTokenizer(propertyList.get(i).getRecord().get(j).toString(),
							":");
					String query = "INSERT INTO " + TABLE2_NAME + " VALUES (" + "'"
							+ propertyList.get(i).getPropertyID() + "',";
					int k = 0;
					while (stoken.hasMoreTokens()) {
						if (k == 5 || k == 4) {
							query += stoken.nextToken() + ",";
							k++;
						} else {
							query += "'" + stoken.nextToken() + "',";
							k++;
						}
					}
					query = query.substring(0, query.length() - 1);
					query += ")";
					result = stmt.executeUpdate(query);
				}
			}

			con.commit();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(e.getClass().getSimpleName());
			alert.setHeaderText("Error!");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}

	public void menuAdd(ActionEvent event) throws Exception {
		try {
			((Node) menubar).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/view/AddWindow.fxml"));
			Scene scene = new Scene(root, 400, 600);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(e.getClass().getSimpleName());
			alert.setHeaderText("Error!");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}

	public void menuRent(ActionEvent event) throws Exception {
		try {
			((Node) menubar).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/view/RentWindow.fxml"));
			Scene scene = new Scene(root, 400, 600);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(e.getClass().getSimpleName());
			alert.setHeaderText("Error!");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}

	public void menuReturn(ActionEvent event) throws Exception {
		try {
			((Node) menubar).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/view/ReturnWindow.fxml"));
			Scene scene = new Scene(root, 400, 400);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(e.getClass().getSimpleName());
			alert.setHeaderText("Error!");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}

	public void menuPerform(ActionEvent event) throws Exception {
		try {
			((Node) menubar).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/view/MaintainWindow.fxml"));
			Scene scene = new Scene(root, 400, 400);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(e.getClass().getSimpleName());
			alert.setHeaderText("Error!");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}

	public void menuComplete(ActionEvent event) throws Exception {
		try {
			((Node) menubar).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/view/CompleteWindow.fxml"));
			Scene scene = new Scene(root, 400, 400);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(e.getClass().getSimpleName());
			alert.setHeaderText("Error!");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}

	public FlowPane getFP() {
		return this.fp;
	}

	public Pane getPane() {
		return this.pane;
	}
}
