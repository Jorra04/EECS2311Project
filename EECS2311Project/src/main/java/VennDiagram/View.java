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
	private AnchorPane anchorPane;
	//private FXMLLoader loader;
	



	public static void main(String[] args) {
		launch(args);
	}

	
	
	@Override
	public void start(Stage stage) throws Exception{
		System.out.printf("root = %s\n", getClass().getResource("/views/View.fxml"));
		Parent root = FXMLLoader.load(getClass().getResource("/views/View.fxml"));
	   
	    Scene scene = new Scene(root, 300, 275);
	
	    stage.setTitle("FVennDiagram");
	    stage.setScene(scene);
	    stage.show();
	}
	

}
