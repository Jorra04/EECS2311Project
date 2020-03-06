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
	    
	    
	    @FXML
	    void refactor(ActionEvent event) {
	    	text = name.getText();
	    	
	    	VennDiagram.refactorWindow.window.close();
	    }
}
