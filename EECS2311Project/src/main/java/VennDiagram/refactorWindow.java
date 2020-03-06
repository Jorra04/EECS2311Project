package VennDiagram;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class refactorWindow {
	public static Stage window;
	
	
	
	public static void display(String title) throws Exception{
		Parent root = FXMLLoader.load(refactorWindow.class.getResource("/views/refactorUI.fxml"));
		window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL); //stops interaction with the other scene while this scene is open.
		window.setTitle(title);
		window.setMinHeight(250);
		window.setMinWidth(300);
		
		

		
		
		
		Scene scene = new Scene(root,300,150);
		window.setScene(scene);
		window.showAndWait();
		
	}
}