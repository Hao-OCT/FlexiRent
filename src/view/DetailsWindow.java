package view;

import java.time.format.DateTimeFormatter;

import controller.MainController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.DateTime;
import model.rentalProperty;
import model.exception.ReturnException;

public class DetailsWindow extends Application {
	private ObservableList<String> detailList;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

	}

	public Pane setDetailUnit(rentalProperty rp) {
		detailList =FXCollections.observableArrayList();
		for(int i=0;i<rp.getRecord().size();i++) {
			detailList.add(rp.getRecord().get(i).getDetails());
		}
		Pane pane = new Pane();
		ListView<String> lv = new ListView<String>();
		lv.setLayoutY(330);
		lv.setPrefWidth(600);
		lv.setItems(detailList);

		// invoke getDetails()
		Label detail = new Label();
		detail.setLayoutY(160);
		detail.setFont(new Font(20));
		detail.setText(rp.getDetails());

		// add picture
		Image image = new Image(rp.getUrl(), 250, 150, false, false);
		ImageView img = new ImageView();
		img.setImage(image);

		pane.getChildren().addAll(detail, img,lv);
		return pane;
	}

	public Pane setDetailFunction(rentalProperty rp) {
		Pane pane = new Pane();
		// add buttons
		Button btn0 = new Button("Back");
		Button btn1 = new Button("Rent");
		Button btn2 = new Button("Return");
		Button btn3 = new Button("Maintain");
		Button btn4 = new Button("Complete");
		btn0.setLayoutX(80);
		btn0.setLayoutY(80);
		btn1.setLayoutX(80);
		btn1.setLayoutY(130);
		btn2.setLayoutX(80);
		btn2.setLayoutY(180);
		btn3.setLayoutX(80);
		btn3.setLayoutY(230);
		btn4.setLayoutX(80);
		btn4.setLayoutY(280);
		
		btn0.setOnAction(e->{
			try {
				((Node) e.getSource()).getScene().getWindow().hide();
				Stage stage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
				Scene scene = new Scene(root, 1000, 800);
				stage.setScene(scene);
				stage.show();
			} catch (Exception e1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle(e1.getClass().getSimpleName());
				alert.setHeaderText("Error!");
				alert.setContentText(e1.getMessage());
				alert.showAndWait();
			}
		});
		// embedded rent function
		btn1.setOnAction(e -> {
			((Node) e.getSource()).getScene().getWindow().hide();
			if (rp.getPropertyStatus().compareTo("Available") != 0) {
				try {
					Stage primaryStage = new Stage();
					Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
					Scene scene = new Scene(root, 1000, 800);
					primaryStage.setScene(scene);
					primaryStage.show();
					throw new ReturnException("This property is not available yet");
				} catch (ReturnException e1) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle(e1.getClass().getSimpleName());
					alert.setHeaderText("Error!");
					alert.setContentText(e1.getMessage());
					alert.showAndWait();
				} catch (Exception e1) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle(e1.getClass().getSimpleName());
					alert.setHeaderText("Error!");
					alert.setContentText(e1.getMessage());
					alert.showAndWait();
				}
			} else {
				Pane pane2 = new Pane();
				Button subBtn1 = new Button("Next");
				subBtn1.setLayoutX(80);
				subBtn1.setLayoutY(230);
				Button subBtn2 = new Button("Back");
				subBtn2.setLayoutX(220);
				subBtn2.setLayoutY(230);

				Label label1 = new Label("Customer ID");
				Label label2 = new Label("Days");
				Label label3 = new Label("Start Date");
				label1.setLayoutX(30);
				label1.setLayoutY(50);
				label2.setLayoutX(30);
				label2.setLayoutY(100);
				label3.setLayoutX(30);
				label3.setLayoutY(150);

				TextField tf1 = new TextField();
				tf1.setLayoutX(120);
				tf1.setLayoutY(50);
				TextField tf2 = new TextField();
				tf2.setLayoutX(120);
				tf2.setLayoutY(100);

				DatePicker dp = new DatePicker();
				dp.setLayoutX(120);
				dp.setLayoutY(150);

				subBtn2.setOnAction(event -> {
					try {
						((Node) event.getSource()).getScene().getWindow().hide();
						Stage primaryStage = new Stage();
						Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
						Scene scene = new Scene(root, 1000, 800);
						primaryStage.setScene(scene);
						primaryStage.show();
					} catch (Exception e1) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle(e1.getClass().getSimpleName());
						alert.setHeaderText("Error!");
						alert.setContentText(e1.getMessage());
						alert.showAndWait();
					}
				});

				subBtn1.setOnAction(event -> {
					// match the property
					try {
						int num = Integer.valueOf(tf2.getText());
						String date, stringDay, stringMonth, stringYear;
						int day, month, year;
						date = dp.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
						stringDay = date.substring(0, 2);
						stringMonth = date.substring(3, 5);
						stringYear = date.substring(6, 10);
						day = Integer.parseInt(stringDay);
						month = Integer.parseInt(stringMonth);
						year = Integer.parseInt(stringYear);
						DateTime dt = new DateTime(day, month, year);

						int index = -1;
						for (int i = 0; i < MainController.propertyList.size(); i++) {
							if (rp.getPropertyID().compareTo(MainController.propertyList.get(i).getPropertyID()) == 0) {
								index = i;
								break;
							}
						}
						if (index != -1) {
							// match the propertyID successfully
							MainController.propertyList.get(index).rent(tf1.getText(), dt, num);
							((Node) event.getSource()).getScene().getWindow().hide();
							Stage primaryStage = new Stage();
							Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
							Scene scene = new Scene(root, 1000, 800);
							primaryStage.setScene(scene);
							primaryStage.show();
						}
					} catch (Exception e1) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle(e1.getClass().getSimpleName());
						alert.setHeaderText("Error!");
						alert.setContentText(e1.getMessage());
						alert.showAndWait();
					}
				});
				pane2.getChildren().addAll(subBtn1, subBtn2, label1, label2, label3, tf1, tf2, dp);
				Scene secondScene = new Scene(pane2, 330, 300);
				Stage Stage = new Stage();
				Stage.setScene(secondScene);
				Stage.show();
			}
		});

		// embedded return function
		btn2.setOnAction(e -> {
			((Node) e.getSource()).getScene().getWindow().hide();
			if (rp.getPropertyStatus().compareTo("Rented") != 0) {
				try {
					Stage primaryStage = new Stage();
					Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
					Scene scene = new Scene(root, 1000, 800);
					primaryStage.setScene(scene);
					primaryStage.show();
					throw new ReturnException("This property is not rented yet");
				} catch (ReturnException e1) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle(e1.getClass().getSimpleName());
					alert.setHeaderText("Error!");
					alert.setContentText(e1.getMessage());
					alert.showAndWait();
				} catch (Exception e1) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle(e1.getClass().getSimpleName());
					alert.setHeaderText("Error!");
					alert.setContentText(e1.getMessage());
					alert.showAndWait();
				}
			} else {
				Pane pane2 = new Pane();
				Button subBtn1 = new Button("Next");
				subBtn1.setLayoutX(80);
				subBtn1.setLayoutY(230);
				Button subBtn2 = new Button("Back");
				subBtn2.setLayoutX(220);
				subBtn2.setLayoutY(230);

				Label label1 = new Label("End Date");
				label1.setLayoutX(30);
				label1.setLayoutY(50);

				DatePicker dp = new DatePicker();
				dp.setLayoutX(120);
				dp.setLayoutY(50);

				subBtn2.setOnAction(event -> {
					try {
						((Node) event.getSource()).getScene().getWindow().hide();
						Stage primaryStage = new Stage();
						Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
						Scene scene = new Scene(root, 1000, 800);
						primaryStage.setScene(scene);
						primaryStage.show();
					} catch (Exception e1) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle(e1.getClass().getSimpleName());
						alert.setHeaderText("Error!");
						alert.setContentText(e1.getMessage());
						alert.showAndWait();
					}
				});
				subBtn1.setOnAction(event -> {
					// match the property
					try {
						String date, stringDay, stringMonth, stringYear;
						int day, month, year;
						date = dp.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
						stringDay = date.substring(0, 2);
						stringMonth = date.substring(3, 5);
						stringYear = date.substring(6, 10);
						day = Integer.parseInt(stringDay);
						month = Integer.parseInt(stringMonth);
						year = Integer.parseInt(stringYear);
						DateTime dt = new DateTime(day, month, year);

						int index = -1;
						for (int i = 0; i < MainController.propertyList.size(); i++) {
							if (rp.getPropertyID().compareTo(MainController.propertyList.get(i).getPropertyID()) == 0) {
								index = i;
								break;
							}
						}
						if (index != -1) {
							// match the propertyID successfully
							MainController.propertyList.get(index).returnProperty(dt);
							((Node) event.getSource()).getScene().getWindow().hide();
							Stage primaryStage = new Stage();
							Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
							Scene scene = new Scene(root, 1000, 800);
							primaryStage.setScene(scene);
							primaryStage.show();
						}
					} catch (Exception e1) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle(e1.getClass().getSimpleName());
						alert.setHeaderText("Error!");
						alert.setContentText(e1.getMessage());
						alert.showAndWait();
					}
				});

				pane2.getChildren().addAll(subBtn1, subBtn2, label1, dp);
				Scene secondScene = new Scene(pane2, 330, 300);
				Stage Stage = new Stage();
				Stage.setScene(secondScene);
				Stage.show();
			}
		});
		// embedded maintain function
		btn3.setOnAction(e -> {
			((Node) e.getSource()).getScene().getWindow().hide();
			if (rp.getPropertyStatus().compareTo("Available") != 0) {
				try {
					Stage primaryStage = new Stage();
					Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
					Scene scene = new Scene(root, 1000, 800);
					primaryStage.setScene(scene);
					primaryStage.show();
					throw new ReturnException("This property is not ready to maintain yet");
				} catch (ReturnException e1) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle(e1.getClass().getSimpleName());
					alert.setHeaderText("Error!");
					alert.setContentText(e1.getMessage());
					alert.showAndWait();
				} catch (Exception e1) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle(e1.getClass().getSimpleName());
					alert.setHeaderText("Error!");
					alert.setContentText(e1.getMessage());
					alert.showAndWait();
				}
			} else {
				int index = -1;
				for (int i = 0; i < MainController.propertyList.size(); i++) {
					if (rp.getPropertyID().compareTo(MainController.propertyList.get(i).getPropertyID()) == 0) {
						index = i;
						break;
					}
				}
				if (index != -1) {
					// match the propertyID successfully
					try {
						MainController.propertyList.get(index).performMaintenance();
						Stage primaryStage = new Stage();
						Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
						Scene scene = new Scene(root, 1000, 800);
						primaryStage.setScene(scene);
						primaryStage.show();
					} catch (Exception e1) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle(e1.getClass().getSimpleName());
						alert.setHeaderText("Error!");
						alert.setContentText(e1.getMessage());
						alert.showAndWait();
					}
				}
			}
		});
		// embedded complete function
		btn4.setOnAction(e -> {
			((Node) e.getSource()).getScene().getWindow().hide();
			if (rp.getPropertyStatus().compareTo("Maintenance") != 0) {
				try {
					Stage primaryStage = new Stage();
					Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
					Scene scene = new Scene(root, 1000, 800);
					primaryStage.setScene(scene);
					primaryStage.show();
					throw new ReturnException("This property is not under maintenance yet");
				} catch (ReturnException e1) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle(e1.getClass().getSimpleName());
					alert.setHeaderText("Error!");
					alert.setContentText(e1.getMessage());
					alert.showAndWait();
				} catch (Exception e1) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle(e1.getClass().getSimpleName());
					alert.setHeaderText("Error!");
					alert.setContentText(e1.getMessage());
					alert.showAndWait();
				}
			} else {
				Pane pane2 = new Pane();
				Button subBtn1 = new Button("Next");
				subBtn1.setLayoutX(80);
				subBtn1.setLayoutY(230);
				Button subBtn2 = new Button("Back");
				subBtn2.setLayoutX(220);
				subBtn2.setLayoutY(230);

				Label label1 = new Label("Complete Date");
				label1.setLayoutX(20);
				label1.setLayoutY(50);

				DatePicker dp = new DatePicker();
				dp.setLayoutX(120);
				dp.setLayoutY(50);

				subBtn2.setOnAction(event -> {
					try {
						((Node) event.getSource()).getScene().getWindow().hide();
						Stage primaryStage = new Stage();
						Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
						Scene scene = new Scene(root, 1000, 800);
						primaryStage.setScene(scene);
						primaryStage.show();
					} catch (Exception e1) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle(e1.getClass().getSimpleName());
						alert.setHeaderText("Error!");
						alert.setContentText(e1.getMessage());
						alert.showAndWait();
					}
				});
				subBtn1.setOnAction(event -> {
					// match the property
					try {
						String date, stringDay, stringMonth, stringYear;
						int day, month, year;
						date = dp.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
						stringDay = date.substring(0, 2);
						stringMonth = date.substring(3, 5);
						stringYear = date.substring(6, 10);
						day = Integer.parseInt(stringDay);
						month = Integer.parseInt(stringMonth);
						year = Integer.parseInt(stringYear);
						DateTime dt = new DateTime(day, month, year);

						int index = -1;
						for (int i = 0; i < MainController.propertyList.size(); i++) {
							if (rp.getPropertyID().compareTo(MainController.propertyList.get(i).getPropertyID()) == 0) {
								index = i;
								break;
							}
						}
						if (index != -1) {
							// match the propertyID successfully
							MainController.propertyList.get(index).completeMaintenance(dt);
							((Node) event.getSource()).getScene().getWindow().hide();
							Stage primaryStage = new Stage();
							Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
							Scene scene = new Scene(root, 1000, 800);
							primaryStage.setScene(scene);
							primaryStage.show();
						}
					} catch (Exception e1) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle(e1.getClass().getSimpleName());
						alert.setHeaderText("Error!");
						alert.setContentText(e1.getMessage());
						alert.showAndWait();
					}
				});

				pane2.getChildren().addAll(subBtn1, subBtn2, label1, dp);
				Scene secondScene = new Scene(pane2, 330, 300);
				Stage Stage = new Stage();
				Stage.setScene(secondScene);
				Stage.show();

			}
		});

		pane.getChildren().addAll(btn0,btn1, btn2, btn3, btn4);
		return pane;
	}

}
