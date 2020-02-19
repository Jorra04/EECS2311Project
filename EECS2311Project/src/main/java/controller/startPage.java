package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import VennDiagram.View;

public class startPage {
	@FXML
	Button next;
	@FXML
	Text title;
	
	
	public void initialize() {
		
	}
	
	
	
	
	
	@FXML
	protected void nextScene() {
		View.primaryStage.setScene(View.scene);
	}
	
}
