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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.DateTime;
import model.exception.ReturnException;
import controller.MainController;

public class ReturnController implements Initializable {
	private ObservableList<String> returnList;
	@FXML
	private DatePicker endDate;
	@FXML
	private Label output;
	@FXML
	private ComboBox<String> cbPID;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// read data from propertyList to select properties that can be return
		returnList=FXCollections.observableArrayList();
		for(int i=0;i<MainController.propertyList.size();i++) {
			if(MainController.propertyList.get(i).getPropertyStatus().compareTo("Rented")==0) {
				returnList.add(MainController.propertyList.get(i).getPropertyID());
			}
		}
		//initialize combo box values
		cbPID.setValue(null);
		cbPID.setItems(returnList);
	}

	public void goNext(ActionEvent event) throws ReturnException {
		String pID, date, stringDay, stringMonth, stringYear;
		int day, month, year;
		try {
			// check input and convert DatePicker value to DateTime
			pID = cbPID.getValue();
			if(cbPID.getValue()!=null) {
			date = endDate.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			stringDay = date.substring(0, 2);
			stringMonth = date.substring(3, 5);
			stringYear = date.substring(6, 10);
			day = Integer.parseInt(stringDay);
			month = Integer.parseInt(stringMonth);
			year = Integer.parseInt(stringYear);
			DateTime dt = new DateTime(day, month, year);

			// match propertyID
			int index = -1;
			for (int i = 0; i < MainController.propertyList.size(); i++) {
				if (pID.compareTo(MainController.propertyList.get(i).getPropertyID()) == 0) {
					index = i;
					break;
				}
			}
			if (index != -1) {
				// match the propertyID successfully
				MainController.propertyList.get(index).returnProperty(dt);
				output.setText("Return Successfully");
			}
			}else {
				throw new ReturnException("You have not selected property ID, if you have nothing to choose it means no property can be returned right now");
			}
		} catch (ReturnException re) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(re.getClass().getSimpleName());
			alert.setHeaderText("Error!");
			alert.setContentText(re.getMessage());
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
