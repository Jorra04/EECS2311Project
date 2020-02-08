package controller;

import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.VennModel;
import model.Item;

public class Controller {

	// create venn diagram instance
	private VennModel model = new VennModel();

	ObservableList<Item> content = FXCollections.observableArrayList();
	//Test
	// fxml components
	@FXML
	TextField create_text;
	@FXML
	Button create_button;
	@FXML
	ListView<Item> item_list;
	@FXML
	Pane diagram_pane; // venn diagram is here
	@FXML
	Button clearButton; // trying to use this clear button
	@FXML
	Button addValues;//adds to set 1
	@FXML
	Button addValues2;//adds to set 2
	@FXML
	Circle c_set1;
	
	
	//implementing enter key for create_text field to add to item_list
	@FXML
	protected void handleCreateTextFieldAction(KeyEvent event) {
		//TODO: refactor handleCreateTextFieldAction and handleCreateButtonAction methods, they both do the same stuff.
		if(event.getCode().equals(KeyCode.ENTER)) {
			if (create_text.getLength() != 0) {
				Item item = new Item(create_text.getText());
				content.add(item);
				item_list.setItems(content);
				create_text.clear();
				create_text.requestFocus();
			}
		}
	}

	@FXML
	protected void handleCreateButtonAction(ActionEvent event){
		if (create_text.getLength() != 0) {
			Item item = new Item(create_text.getText());
			content.add(item);
			item_list.setItems(content);
			create_text.clear();
			create_text.requestFocus();
		}
	}
	
	@FXML
	protected void handleClearAllButtonAction(ActionEvent event) {
		item_list.getItems().clear();
	}

	@FXML
	protected void handleClearSelectedButtonAction(ActionEvent event) {
		List<Item> copyList = new ArrayList<>(item_list.getSelectionModel().getSelectedItems());
		item_list.getItems().removeAll(copyList);
		
	}
	@FXML
	protected void handleaddValues1ButtonAction(ActionEvent event) {
		
		//Make sure that item_list has values in it AND a user has actually clicked a selected item
		if(item_list.getItems().size() !=0 && item_list.getSelectionModel().getSelectedItem() != null) {
		//Get the name of the added task	
		String addedTask = item_list.getSelectionModel().getSelectedItem().getText();
		}
		
	}
	
	@FXML
	protected void handleaddValues2ButtonAction(ActionEvent event) {
		
		if(item_list.getItems().size() !=0 && item_list.getSelectionModel().getSelectedItem() != null) {
			String addedTask = item_list.getSelectionModel().getSelectedItem().getText();
			
			}	
		}

}