package controller;

import model.VennModel;
import VennDiagram.View;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class Controller {
	VennModel model;
	//view here
	View view;
	public Controller(VennModel model, View view) {
		this.model = model;
		this.view = view;
	}
	private void handleButtonAction(ActionEvent event)
    {
        Button button = (Button)event.getSource();
    }
	
}
