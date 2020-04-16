package VennDiagram;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class invalidFileFormatAlert {
	public static Stage window;
	protected static Button ok = new Button("Ok");
	public static boolean okPressed;
	
	
	public static void display(String title, String message) {
		window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL); //stops interaction with the other scene while this scene is open.
		window.setTitle(title);
		window.setMinHeight(100);
		window.setMinWidth(280);
		window.setResizable(false);
		
		Label label = new Label();
		label.setText(message);
		
		ok.setOnAction(e -> {
			okPressed = true;
			window.close();
		});
		
		StackPane layout = new StackPane();
		layout.getChildren().addAll(label,ok);
		StackPane.setAlignment(label, Pos.TOP_CENTER);
		StackPane.setMargin(label, new Insets(5,0,0,0));
		StackPane.setAlignment(ok, Pos.BOTTOM_CENTER);
		StackPane.setMargin(ok, new Insets(0,0,5,0));
		
		Scene scene = new Scene(layout);
		scene.getStylesheets().add(clearAllAlert.class.getResource("alerts.css").toExternalForm());
		window.setScene(scene);
		window.showAndWait();
		
		
	}
}
