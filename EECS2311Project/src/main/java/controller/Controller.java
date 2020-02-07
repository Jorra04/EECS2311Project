package controller;

import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import model.VennModel;
import model.Item;

public class Controller {

	// create venn diagram instance
	private VennModel model = new VennModel();

	ObservableList<String> content = FXCollections.observableArrayList();
	int removed = 0;

	// fxml components
	@FXML
	TextField create_text;
	@FXML
	Button create_button;
	@FXML
	ListView<String> item_list;
	@FXML
	Pane diagram_pane; // venn diagram is here
	@FXML
	Button clearButton; // trying to use this clear button

	//implementing enter key for create_text field to add to item_list
	@FXML
	protected void handleCreateTextFieldAction(KeyEvent event) {
		//TODO: refactor handleCreateTextFieldAction and handleCreateButtonAction methods, they both do the same stuff.
		if(event.getCode().equals(KeyCode.ENTER)) {
			if (create_text.getLength() != 0) {
				Item item = new Item(create_text.getText());
				content.add(item.toString());
				item_list.setItems(content);
				create_text.clear();
				create_text.requestFocus();
			}
		}
	}

	@FXML
	protected void handleCreateButtonAction(ActionEvent event) {
		if (create_text.getLength() != 0) {
			Item item = new Item(create_text.getText());
			content.add(item.toString());
			item_list.setItems(content);
			item_list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			create_text.clear();
			create_text.requestFocus();
		}
	}

	@FXML
	protected void handleClearAllButtonAction(ActionEvent event) {
		
		Item.uid =0;
		item_list.getItems().clear();
		create_text.requestFocus();
	}

	@FXML
	protected void handleClearSelectedButtonAction(ActionEvent event) {
		List<String> copyList = new ArrayList<>(item_list.getSelectionModel().getSelectedItems());
		item_list.getItems().removeAll(copyList);
		removed++;
		Item.uid -=removed;
		create_text.requestFocus();
	}

}