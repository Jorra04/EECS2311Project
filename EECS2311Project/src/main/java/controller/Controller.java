package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.VennModel;
import model.Item;
 
public class Controller {
	
	//create venn diagram instance
    private VennModel model = new VennModel();
	
	//fxml components
	@FXML TextField create_text;
	@FXML Button create_button;
    
    @FXML 
    protected void handleCreateButtonAction(ActionEvent event) {
        model.addItem(new Item(create_text.getText()));
        for(Item item : model.getItemList()) {
            System.out.print(item.toString());
        }
    }

}