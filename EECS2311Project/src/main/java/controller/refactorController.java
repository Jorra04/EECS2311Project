package controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
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
	static Color color;

	public static String text = "";
	public static String description = "";
	public static boolean buttonPressed;
	private String validationError = "-fx-border-color: #DBB1B1; " + "-fx-background-color: #FFF0F0";
	Tooltip tooltip = new Tooltip("Item text cannot be empty");

	public void initialize() {
		origStyle = name.getStyle();
		buttonPressed = false;
		name.setText(text);
		itemDescription.setText(description);
	}

	@FXML
	void refactor(ActionEvent event) {
		name.setStyle(origStyle);
		if (name.getText().isEmpty()) {
			Shaker shaker = new Shaker(name);
			shaker.shake();
			name.setStyle(validationError);
			Tooltip.install(name, tooltip);
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
		System.out.println(changeLabelColor.getValue());
		color = changeLabelColor.getValue();
	}
}
