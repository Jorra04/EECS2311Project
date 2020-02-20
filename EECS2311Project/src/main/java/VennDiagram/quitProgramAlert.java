package VennDiagram;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class quitProgramAlert {
	public static Stage window;
	protected static Button quit = new Button("Quit");
	protected static Button cancel = new Button("Cancel");
	public static boolean cancelPressed, closePressed;
	
	
	public static void display(String title, String message) {
		window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL); //stops interaction with the other scene while this scene is open.
		window.setTitle(title);
		window.setMinHeight(100);
		window.setMinWidth(280);
		
		Label label = new Label();
		label.setText(message);
		
		quit.setOnAction(e -> {
			closePressed = true;
			window.close();
		});
		cancel.setOnAction(e-> {
			cancelPressed = true;
			window.close();
		});
		
		StackPane layout = new StackPane();
		layout.getChildren().addAll(label,quit,cancel);
		StackPane.setAlignment(label, Pos.TOP_CENTER);
		StackPane.setMargin(label, new Insets(5,0,0,0));
		StackPane.setAlignment(quit, Pos.CENTER_LEFT);
		StackPane.setMargin(quit, new Insets(30,0,10,60));
		StackPane.setAlignment(cancel, Pos.CENTER_RIGHT);
		StackPane.setMargin(cancel, new Insets(30,60,10,0));
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
		
	}
}
