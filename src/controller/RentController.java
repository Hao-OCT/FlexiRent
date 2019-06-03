package controller;

import java.net.URL;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.DateTime;
import model.exception.RentException;

public class RentController implements Initializable {

	private ObservableList<String> typeList = FXCollections.observableArrayList("Apartment", "Premium Suite");
	@FXML
	private TextField customerID, streetNum, streetName, suburb, days;
	@FXML
	private ChoiceBox<String> type;
	@FXML
	private DatePicker startDate;
	@FXML
	private Label output;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		type.setValue(null);
		type.setItems(typeList);
	}

	public void goNext(ActionEvent event) throws RentException {
		String pID,CID, sName = "", sub = "", propertyType = "", date, stringDay, stringMonth, stringYear;
		int sNum = 0, numofRent, day, month, year;
		try {
			// check input.
			CID = customerID.getText();
			sNum = Integer.parseInt(streetNum.getText());
			sName = streetName.getText();
			sub = suburb.getText();
			propertyType = (String) type.getValue();
			numofRent = Integer.parseInt(days.getText());
			date = startDate.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			stringDay = date.substring(0, 2);
			stringMonth = date.substring(3, 5);
			stringYear = date.substring(6, 10);
			day = Integer.parseInt(stringDay);
			month = Integer.parseInt(stringMonth);
			year = Integer.parseInt(stringYear);
			DateTime dt = new DateTime(day, month, year);
			
			//Generate property ID by input
			if(propertyType.compareTo("Apartment")==0) {
				pID ="A_"+sNum+"_"+sName+"_"+sub;
			}else {
				pID ="S_"+sNum+"_"+sName+"_"+sub;
			}	
			// match propertyID
			int index = -1;
			for (int i = 0; i < MainController.propertyList.size(); i++) {
				if (pID.compareTo(MainController.propertyList.get(i).getPropertyID())==0) {
					index = i;
					break;
				}
			}
			if (index != -1) {
				// match the propertyID successfully
				MainController.propertyList.get(index).rent(CID, dt, numofRent);
				output.setText("Rent Successfully");
			} else {
				throw new RentException("Cannot match the property ID");
			}
		} catch (RentException re) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(re.getClass().getSimpleName());
			alert.setHeaderText("Error!");
			alert.setContentText(re.getMessage());
			alert.showAndWait();
		} catch (Exception e) {
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
}
