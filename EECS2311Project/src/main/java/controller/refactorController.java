package controller;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class refactorController {
	 @FXML
	    private TextField name;

	    @FXML
	    private Button refactor;
	    
	    public static String text = "";
	    public static boolean buttonPressed;
	    
	    public void initialize() {
	    	buttonPressed = false;
	    }
	    
	    
	    @FXML
	    void refactor(ActionEvent event) {
	    	text = name.getText();
	    	buttonPressed = true;
	    	System.out.println(buttonPressed);
	    	VennDiagram.refactorWindow.window.close();
	    }
}
