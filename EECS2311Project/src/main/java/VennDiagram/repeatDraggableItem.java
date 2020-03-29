package VennDiagram;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class repeatDraggableItem {
	public static Stage window;
	static Button confirm = new Button("Okay, Got It.");
	private static boolean confirmPressed;
	public static boolean checkboxPressed;
	
	
	public static void display(String title, String message) {
		window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL); //stops interaction with the other scene while this scene is open.
		window.setTitle(title);
		window.setMinHeight(200);
		window.setMinWidth(300);
		
		Label label = new Label();
		label.setText(message);
		CheckBox checkbox = new CheckBox("I want to add repeating elements.");

		confirm.setOnAction(e-> {
			confirmPressed = true;
			window.close();
		});
		
		
		checkbox.setOnAction(e->{
			checkboxPressed = true;
		});
		
		StackPane layout = new StackPane();
		layout.getChildren().addAll(label,confirm,checkbox);
		StackPane.setAlignment(label, Pos.TOP_CENTER);
		StackPane.setMargin(label, new Insets(5,0,0,0));
		StackPane.setAlignment(confirm, Pos.CENTER);
		StackPane.setMargin(confirm, new Insets(20,0,0,0));
		StackPane.setMargin(checkbox, new Insets(65,0,0,0));
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
		
	}
}
