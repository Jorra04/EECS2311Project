package controller;

import java.util.Timer;

import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import model.Shaker;

public class refactorController {

	@FXML
	private AnchorPane mainPane;

	@FXML
	private TextField name;

	@FXML
	private TextField itemDescription;

	@FXML
	private Button refactor;

	@FXML
	private ColorPicker changeLabelColor;

	private String origStyle;
	static Color color = Color.BLACK;

	public static String text = "";
	public static String description = "";
	public static boolean buttonPressed;
	private String validationError = "-fx-border-color: #DBB1B1; " + "-fx-background-color: #FFF0F0";
	Tooltip tooltip = new Tooltip("Item text cannot be empty");

	public void initialize() {
		origStyle = name.getStyle();
		buttonPressed = false;
		changeLabelColor.setValue(Color.BLACK);
//		animation stuff.
		RotateTransition rt = new RotateTransition(Duration.millis(200),refactor);
		
		rt.setFromAngle(-10);
		rt.setToAngle(10);
//		rt.setByAngle(10);
		rt.setOnFinished(e->{
			refactor.setRotate(0);
		});
		
		
		rt.setAutoReverse(true);
		rt.setCycleCount(5);
		refactor.setOnMouseEntered(e->{
			rt.play();
		});
		refactor.setOnMouseExited(e->{
			rt.stop();
			refactor.setRotate(0);
		});
		
		
		
	}

	@FXML
	void refactor(ActionEvent event) {
		name.setStyle(origStyle);
		if (name.getText().isEmpty()) {
			Shaker shaker = new Shaker(name);
			shaker.shake();
			name.setStyle(validationError);
			Tooltip.install(name, tooltip);
			return;
		}
		if (itemDescription.getText().isEmpty()) {
			description = "No Description";
			buttonPressed = true;
			text = name.getText();
	
			VennDiagram.refactorWindow.window.close();
			
			return;
		}

		text = name.getText();
		description = itemDescription.getText();
		buttonPressed = true;
		VennDiagram.refactorWindow.window.close();

	}
	
	@FXML
	protected void changeColor(ActionEvent event) {
		color = changeLabelColor.getValue();
	}
}
