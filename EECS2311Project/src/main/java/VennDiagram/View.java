package VennDiagram;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.application.Application;
import javafx.event.EventHandler;
//import javafx.event.EventHandler;
//import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
//import javafx.scene.layout.StackPane;
import javafx.scene.Group;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.shape.Circle;

	// Button button;

public class View extends Application{
	public static Stage primaryStage;
	private AnchorPane anchorPane;
	//private FXMLLoader loader;
	
	//Button button;
	model.Item item;
    @FXML
    private Circle circle1;

    @FXML
    private Circle circle2;

    @FXML
    private ListView<?> listView;

    @FXML
    private TextField textField1;

    @FXML
    private Button button1;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

			View.primaryStage = primaryStage;
			FXMLLoader loader1 = new FXMLLoader();
			loader1.setLocation(getClass().getResource("View.fxml"));
			this.anchorPane = (AnchorPane) loader1.load();
			Scene scene = new Scene(this.anchorPane);
			View.primaryStage.setScene(scene);
			View.primaryStage.show();
			


	}
//	public void loader() throws Exception{
//		this.loader = new FXMLLoader();
//		this.loader.setLocation(getClass().getResource("View.fxml"));
//		this.anchorPane = (AnchorPane)loader.load();
//	}

}
