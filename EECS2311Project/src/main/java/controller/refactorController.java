package controller;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import model.Shaker;

public class refactorController {
	 	@FXML
	    private TextField name;

	    private Button refactor;
	    private String origStyle;
	    
	    @FXML
	    private TextField itemDescription;
	    
	    public static String text = "";
	    public static String description = "";
	    public static boolean buttonPressed;
	    private String validationError = "-fx-border-color: #DBB1B1; " + "-fx-background-color: #FFF0F0";
	    Tooltip tooltip = new Tooltip("Item text cannot be empty");
	    
	    @FXML
	    public void initialize() {
	    	buttonPressed = false;
	    	origStyle = name.getStyle();
	    }
	    
	    
	    @FXML
	    void refactor(ActionEvent event) {
	    	name.setStyle(origStyle);
	    	if(name.getText().isEmpty()) {
	    		Shaker shaker = new Shaker(name);
	    		shaker.shake();
	    		name.setStyle(validationError);
	    		Tooltip.install(name, tooltip);
	    		return;
	    	}
	    	if(itemDescription.getText().isEmpty()) {
	    		description= "No Description";
	    	}
	    	
	    	text = name.getText();
	    	description = itemDescription.getText();
	    	buttonPressed = true;
	    	VennDiagram.refactorWindow.window.close();
	    }
	    
}
