package VennDiagram;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.shape.Circle;

//merged




public class View extends Application{
	public static Stage primaryStage;
	//private FXMLLoader loader;


	public static void main(String[] args) {
		launch(args);
	}

	
	

	@Override
	public void start(Stage stage) throws Exception{
//		System.out.printf("root = %s\n", getClass().getResource("/views/View.fxml"));
		Parent root = FXMLLoader.load(getClass().getResource("/views/View.fxml"));
	   
	    Scene scene = new Scene(root, 800, 600);
	
	    stage.setTitle("VennDiagram Creator");
	    stage.setScene(scene);
	    stage.setResizable(true);
	    stage.show();
	}
	

}
