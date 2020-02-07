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
import javafx.scene.layout.Pane;
import model.VennModel;
import model.Item;

public class Controller {

	// create venn diagram instance
	private VennModel model = new VennModel();

	ObservableList<String> content = FXCollections.observableArrayList();

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

	@FXML
	protected void handleCreateButtonAction(ActionEvent event){
		if (create_text.getLength() != 0) {
			Item item = new Item(create_text.getText());
			content.add(item.toString());
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
		List<String> copyList = new ArrayList<>(item_list.getSelectionModel().getSelectedItems());
		item_list.getItems().removeAll(copyList);
	}

}