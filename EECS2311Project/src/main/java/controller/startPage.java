package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import VennDiagram.View;

public class startPage {

    @FXML
    private AnchorPane anchorpane;

    @FXML
    private Text title;

    @FXML
    private Button next;
    
    @FXML
    private ImageView imView;
	
	
	
	
	
	
	
	@FXML
	protected void nextScene() {
		View.primaryStage.setScene(View.scene);
	}
	
}
