package view;

import java.time.format.DateTimeFormatter;

import controller.MainController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.DateTime;
import model.rentalProperty;
import model.exception.ReturnException;

public class OverviewUnit extends Application {
	private Label label2;
	private Button btn1;
	private ImageView img;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

	}

	public Pane setUnit(rentalProperty rp) {
		StackPane pane = new StackPane();
		Pane root = new Pane();
		pane.setStyle("-fx-background-color:lightgrey;");
		pane.prefHeight(250);
		pane.prefWidth(250);
		root.setPrefSize(250, 250);

		btn1 = new Button("book");
		btn1.setLayoutX(200);
		btn1.setLayoutY(150);
		btn1.setOnAction(e -> {
			((Node) e.getSource()).getScene().getWindow().hide();
			if (rp.getPropertyStatus().compareTo("Available") != 0) {
				try {
					Stage primaryStage = new Stage();
					Parent root2 = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
					Scene scene = new Scene(root2, 1000, 800);
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
						Parent root2 = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
						Scene scene = new Scene(root2, 1000, 800);
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
							Parent root2 = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
							Scene scene = new Scene(root2, 1000, 800);
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

		if (rp.getUrl() == null) {
			rp.setUrl("image/default.png");
		}
		Image image = new Image(rp.getUrl(), 250, 150, false, false);
		img = new ImageView();
		img.setImage(image);

		Label label = new Label();
		label.setWrapText(true);
		label.setFont(new Font(18));
		label.setMaxWidth(250);
		label.setLayoutX(0);
		label.setLayoutY(150);
		label.setText(rp.getStreetNum() + " " + rp.getStreetName() + "\n" + rp.getDesc());

		label2 = new Label("Info");
		label2.setFont(new Font(18));
		label2.setLayoutX(150);
		label2.setLayoutY(150);

		pane.getChildren().add(root);
		root.getChildren().addAll(img, label, label2, btn1);

		return pane;
	}

	class bookHandlerClass implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			// TODO Auto-generated method stub
			try {
				((Node) event.getSource()).getScene().getWindow().hide();
				Stage stage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("/view/RentWindow.fxml"));
				Scene scene = new Scene(root, 400, 600);
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

	public Label getInfo() {
		return this.label2;
	}

	public ImageView getImg() {
		return this.img;
	}
}
