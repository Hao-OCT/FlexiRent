package controller;

import java.net.URL;
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
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import model.exception.PerformException;
import controller.MainController;

public class MaintainController implements Initializable {
	private ObservableList<String> maintainList;
	@FXML
	private Label output;
	@FXML
	private ComboBox<String> cbPID;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// read data from propertyList to select properties that can be return
		maintainList = FXCollections.observableArrayList();
		for (int i = 0; i < MainController.propertyList.size(); i++) {
			if (MainController.propertyList.get(i).getPropertyStatus().compareTo("Available") == 0) {
				maintainList.add(MainController.propertyList.get(i).getPropertyID());
			}
		}
		// initialize combo box values
		cbPID.setValue(null);
		cbPID.setItems(maintainList);
	}

	public void goNext() throws PerformException {
		String pID;
		try {
			pID = cbPID.getValue();// match propertyID
			if (cbPID.getValue() != null) {
				int index = -1;
				for (int i = 0; i < MainController.propertyList.size(); i++) {
					if (pID.compareTo(MainController.propertyList.get(i).getPropertyID()) == 0) {
						index = i;
						break;
					}
				}
				if (index != -1) {
					// match the propertyID successfully
					MainController.propertyList.get(index).performMaintenance();
					output.setText("Perform maintenance Successfully");
				}
			} else {
				throw new PerformException(
						"You have not selected property ID, if you have nothing to choose it means no property can be maintained right now");
			}
		} catch (PerformException pe) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(pe.getClass().getSimpleName());
			alert.setHeaderText("Error!");
			alert.setContentText(pe.getMessage());
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
