package VennDiagram;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class quitProgram {
	public static Stage window;
	static Button closeButton = new Button("Quit");
	static Button cancel = new Button("Cancel");
	public static boolean cancelPressed, closePressed;
	
	
	public static void display(String title, String message) {
		window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL); //stops interaction with the other scene while this scene is open.
		window.setTitle(title);
		window.setMinHeight(120);
		window.setMinWidth(400);
		
		Label label = new Label();
		label.setText(message);
		
		closeButton.setOnAction(e -> {
			closePressed = true;
			window.close();
		});
		cancel.setOnAction(e-> {
			cancelPressed = true;
			window.close();
		});
		
		StackPane layout = new StackPane();
		layout.getChildren().addAll(label,closeButton,cancel);
		StackPane.setAlignment(label, Pos.TOP_CENTER);
		StackPane.setMargin(label, new Insets(5,0,0,0));
		StackPane.setAlignment(closeButton, Pos.CENTER_LEFT);
		StackPane.setMargin(closeButton, new Insets(30,0,10,90));
		StackPane.setAlignment(cancel, Pos.CENTER_RIGHT);
		StackPane.setMargin(cancel, new Insets(30,90,10,0));
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
		
	}
}
