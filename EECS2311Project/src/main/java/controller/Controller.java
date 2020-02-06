package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import model.VennModel;
import model.Item;
 
public class Controller {
	
	//create venn diagram instance
    private VennModel model = new VennModel();
    
    ObservableList<String> content = FXCollections.observableArrayList();
	
	//fxml components
	@FXML TextField create_text;
	@FXML Button create_button;
	@FXML ListView<String> item_list;
	@FXML Pane diagram_pane; //venn diagram is here
    
    @FXML 
    protected void handleCreateButtonAction(ActionEvent event) {
        model.addItem(new Item(create_text.getText()));
        for(Item item : model.getItemList()) {
        	content.add(item.toString());
        }
        item_list.setItems(content);
    }

}