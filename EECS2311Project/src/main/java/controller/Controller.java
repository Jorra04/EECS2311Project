package controller;

import model.VennModel;
import VennDiagram.Main;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class Controller {
	VennModel model;
	//view here
	Main main;
	public Controller(VennModel model, Main main) {
		this.model = model;
		this.main = main;
	}
	private void handleButtonAction(ActionEvent event)
    {
        Button button = (Button)event.getSource();
    }
	
}
