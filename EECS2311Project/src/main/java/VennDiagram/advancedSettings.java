package VennDiagram;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class advancedSettings {
	public static Stage window;
	
	
	
	public static void display(String title) throws Exception{
		Parent root = FXMLLoader.load(refactorWindow.class.getResource("/views/extendedSettings.fxml"));
		window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL); //stops interaction with the other scene while this scene is open.
		window.setTitle(title);
		
		Scene scene = new Scene(root,600,400);

//		scene.getStylesheets().add(refactorWindow.class.getResource("advancedSettings.css").toExternalForm());
		window.setResizable(false);
		window.setScene(scene);
		window.showAndWait();
		
	}
}