package VennDiagram;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class View extends Application{
	
	@Override
	public void start(Stage stage) throws Exception{
//		System.out.printf("root = %s\n", getClass().getResource("/views/View.fxml"));
		Parent root = FXMLLoader.load(getClass().getResource("/views/View.fxml"));
	   
	    Scene scene = new Scene(root, 300, 275);
	
	    stage.setTitle("FVennDiagram");
	    stage.setScene(scene);
	    stage.show();
	}
	
}
