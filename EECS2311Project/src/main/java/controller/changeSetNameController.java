package controller;

import java.util.Timer;

import javafx.animation.RotateTransition;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import model.Shaker;

public class changeSetNameController {

	@FXML
	private AnchorPane mainPane;

	@FXML
    private Text title;
	
	@FXML
	private TextField name;

	@FXML
	private Button refactor;

	@FXML
	private ColorPicker changeLabelColor;

	private String origStyle;
	public static Color color = Color.BLACK;

	public static String text = "";
	public static boolean buttonPressed;
	private String validationError = "-fx-border-color: #DBB1B1; " + "-fx-background-color: #FFF0F0";
	
	public void initialize() {
		
		name.setText(text);
		origStyle = name.getStyle();
		buttonPressed = false;
		changeLabelColor.setValue(color);
		title.setStyle("-fx-text-fill: white");
		
		refactor.setOnMouseEntered(e->{
			refactor.setScaleX(1.1);
			refactor.setScaleY(1.1);
			refactor.setScaleZ(1.1);
		});
		refactor.setOnMouseExited(e->{
			refactor.setScaleX(1);
			refactor.setScaleY(1);
			refactor.setScaleZ(1);
		});
	}

	@FXML
	void refactor(ActionEvent event) {
		name.setStyle(origStyle);
		if (name.getText().isEmpty()) {
			Shaker shaker = new Shaker(name);
			shaker.shake();
			name.setStyle(validationError);
			return;
		}


		text = name.getText();
		buttonPressed = true;
		VennDiagram.changeSetNameWindow.window.close();

	}
	
	@FXML
	protected void changeColor(ActionEvent event) {
		color = changeLabelColor.getValue();
	}
}