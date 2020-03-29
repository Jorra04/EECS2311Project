package controller;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class refactorController {
	 	@FXML
	    private TextField name;

	    private Button refactor;
	    
	    
	    @FXML
	    private TextField itemDescription;
	    
	    public static String text = "";
	    public static String description = "";
	    public static boolean buttonPressed;
	    
	    @FXML
	    public void initialize() {
	    	buttonPressed = false;
	    }
	    
	    
	    @FXML
	    void refactor(ActionEvent event) {
	    	text = name.getText();
	    	description = itemDescription.getText();
	    	buttonPressed = true;
	    	VennDiagram.refactorWindow.window.close();
	    }
}
