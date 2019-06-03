package controller;

import java.io.File;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.DateTime;
import model.apartment;
import model.premiumSuite;
import model.rentalProperty;
import model.exception.AddException;

public class AddController implements Initializable {
	List<String> listFile;
	private ObservableList<String> typeList = FXCollections.observableArrayList("Apartment", "Premium Suite");

	@FXML
	private Button btnFileChooser;
	@FXML
	private TextField streetNum, streetName, suburb, numofRooms;
	@FXML
	private ChoiceBox<String> type;
	@FXML
	private DatePicker lastDate;
	@FXML
	private Label output, labelFilePath;
	@FXML
	private TextArea desc;

	@FXML
	void fileChoose(ActionEvent event) {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new ExtensionFilter("Image files", listFile));
		File f = fc.showOpenDialog(null);
		if (f != null) {
			labelFilePath.setText(f.getName());
		} else {
			labelFilePath.setText("default.png");
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		type.setValue("Apartment");
		type.setItems(typeList);
		listFile = new ArrayList<>();
		listFile.add("*.jpg");
		listFile.add("*.jpeg");
		listFile.add("*.png");
		listFile.add("*.JPG");
		listFile.add("*.JPEG");
		listFile.add("*.PNG");
	}

	public void goNext(ActionEvent event) throws AddException {
		String pID, sName = "", sub = "", propertyType = "", url, description, date, stringDay, stringMonth, stringYear;
		int sNum = 0, day, month, year, rooms;
		try {
			propertyType = (String) type.getValue();
			if (propertyType.compareTo("Apartment") == 0) {

				// add an apartment
				sNum = Integer.parseInt(streetNum.getText());
				sName = streetName.getText();
				sub = suburb.getText();
				url = "image/" + labelFilePath.getText();
				description = desc.getText();
				rooms = Integer.parseInt(numofRooms.getText());

				if (rooms > 0 && rooms < 4) {
					// Generate property ID by input
					pID = "A_" + sNum + "_" + sName + "_" + sub;

					// match existing ID or not
					for (int i = 0; i < MainController.propertyList.size(); i++) {
						if (pID.compareTo(MainController.propertyList.get(i).getPropertyID()) == 0) {
							throw new AddException("The Property already exists.");
						}
					}
					rentalProperty apt = new apartment(sNum, sName, sub, rooms, propertyType, url, description);
					MainController.propertyList.add(apt);
					output.setText("Add apartment: " + pID + " Successfully");

				} else {
					throw new AddException("Number of bedrooms should between 1-3.");
				}
			} else {
				// add a premium suite
				sNum = Integer.valueOf(streetNum.getText());
				sName = streetName.getText();
				sub = suburb.getText();
				url = labelFilePath.getText();
				description = desc.getText();

				// Generate property ID by input
				pID = "S_" + sNum + "_" + sName + "_" + sub;

				// convert DataPicker to DateTime
				date = lastDate.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
				stringDay = date.substring(0, 2);
				stringMonth = date.substring(3, 5);
				stringYear = date.substring(6, 10);
				day = Integer.parseInt(stringDay);
				month = Integer.parseInt(stringMonth);
				year = Integer.parseInt(stringYear);
				DateTime dt = new DateTime(day, month, year);

				// match existing ID or not
				for (int i = 0; i < MainController.propertyList.size(); i++) {
					if (pID.compareTo(MainController.propertyList.get(i).getPropertyID()) == 0) {
						throw new AddException("The Property already exists.");
					}
				}
				rentalProperty ps = new premiumSuite(sNum, sName, sub, 3, propertyType, url, description, dt);
				MainController.propertyList.add(ps);
				output.setText("Add Premium Suite: " + pID + " Successfully");

			}
		} catch (AddException ae) {
			// TODO: handle exception
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(ae.getClass().getSimpleName());
			alert.setHeaderText("Error!");
			alert.setContentText(ae.getMessage());
			alert.showAndWait();
		} catch (Exception e) {
			// TODO: handle exception
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(e.getClass().getSimpleName());
			alert.setHeaderText("Error!");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}

	public void goBack(ActionEvent event) {
		try {
			((Node) event.getSource()).getScene().getWindow().hide();
			Stage stage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
			Scene scene = new Scene(root, 1000, 800);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(e.getClass().getSimpleName());
			alert.setHeaderText("Error!");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}

	}

	public Label getFileName() {
		return this.labelFilePath;
	}

}
