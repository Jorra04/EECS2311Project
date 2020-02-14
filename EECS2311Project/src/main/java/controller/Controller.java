package controller;

import java.util.*;

import com.sun.jdi.event.Event;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.VennModel;
import model.Group;
import model.Item;
import VennDiagram.View;;

public class Controller {

	//View.primaryStage.setScene(View.promptWindow); --> code to switch windows.
	// create venn diagram instance
	int removed = 0;
	private VennModel model;

	ObservableList<Item> itemsContent = FXCollections.observableArrayList();
	ObservableList<Item> selectedItems;
	Group leftGroup;
	Group rightGroup;
	Group matchGroup;
	private static final DataFormat itemFormat = new DataFormat("item");

	// fxml components
	@FXML
	Circle leftCircle;
	@FXML
	Circle rightCircle;

	@FXML
	SplitMenuButton menuButton = new SplitMenuButton();

	@FXML
	MenuItem lButton = new MenuItem();
	@FXML
	MenuItem rButton = new MenuItem();

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
	StackPane leftSet, rightSet, middleSet;
	@FXML
	Text leftSetText, rightSetText, middleSetText;

	private static ColorPicker colorPicker = new ColorPicker(Color.DODGERBLUE);
	private static VBox box = new VBox(colorPicker);

	// use this to help setup the fxml components, initialize is called as soon as
	// app starts up. Similar to a constructor.
	public void initialize() {
		model = new VennModel();
		// setup content list, item_list reflects the observable list itemsContent
		itemsContent.setAll(model.getItemList());
		item_list.setItems(itemsContent);

		// item_list allow multiple selection
		item_list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		selectedItems = item_list.getSelectionModel().getSelectedItems();

		// setup 2 groups
		model.createGroup("Left");
		model.createGroup("Right");
		model.createGroup("Match");
		leftGroup = model.getGroupMap().get("Left");
		rightGroup = model.getGroupMap().get("Right");
		matchGroup = model.getGroupMap().get("Match");

		// customizing the cell, how they look on the listview in the gui
		item_list.setCellFactory(param -> new ListCell<Item>() {
			@Override
			protected void updateItem(Item item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null || item.getText() == null) {
					setText(null);
				} else {
					setText(item.getText());
				}
			}
		});
	}

	// listview is not serializable, so convert to arraylist which is.
	private ArrayList<Item> obsListToArrayList(ObservableList<Item> list) {
		return new ArrayList<Item>(list);
	}

	// implementing enter key for create_text field to add to item_list
	@FXML
	protected void handleCreateTextFieldAction(KeyEvent event) {
		// TODO: refactor handleCreateTextFieldAction and handleCreateButtonAction
		// methods, they both do the same stuff.
		if (event.getCode().equals(KeyCode.ENTER)) {
			if (create_text.getLength() != 0) {
				Item item = new Item(create_text.getText());

				model.getItemList().add(item);
				itemsContent.setAll(model.getItemList());
				
				create_text.clear();
				create_text.requestFocus();
			}
		}
		event.consume();
	}

	@FXML
	protected void handleCreateButtonAction(ActionEvent event) {
		if (create_text.getLength() != 0) {
			Item item = new Item(create_text.getText());
			model.getItemList().add(item);
			itemsContent.setAll(model.getItemList());
			// create_button.setTextFill(Color.RED);
			rightCircle.setFill(Color.RED);
			create_text.clear();
			create_text.requestFocus();

		}
		event.consume();
	}

	@FXML
	protected void menuClick() {
		menuButton.getItems().add(lButton);
		menuButton.getItems().add(rButton);

	}

	@SuppressWarnings("unchecked")
	@FXML
	protected void leftCircleColour() {
		if (diagram_pane.getChildren().contains(Controller.box)) {
			diagram_pane.getChildren().remove(Controller.box);
			Color colorVal = (Color)leftCircle.getFill();
			colorPicker.setValue(colorVal);
		}
		//colorPicker.set

		diagram_pane.getChildren().add(Controller.box);
		box.setLayoutX(14);
		box.setLayoutY(menuButton.getLayoutY() + 50);

		colorPicker.setOnAction(new EventHandler() {
			@Override
			public void handle(javafx.event.Event event) {
				leftCircle.setFill(colorPicker.getValue());
				leftCircle.setStroke(Color.BLACK);

			}
		});

	}

	// to change the colour of the right circle;
	@SuppressWarnings("unchecked")
	@FXML
	protected void rightCircleColour() {
		if (diagram_pane.getChildren().contains(Controller.box)) {
			diagram_pane.getChildren().remove(Controller.box);
			Color colorVal = (Color)rightCircle.getFill();
			colorPicker.setValue(colorVal);
		}
		diagram_pane.getChildren().add(Controller.box);
		box.setLayoutX(14);
		box.setLayoutY(menuButton.getLayoutY() + 50);
		colorPicker.setOnAction(new EventHandler() {
			public void handle(javafx.event.Event event) {
				// TODO Auto-generated method s
				rightCircle.setFill(colorPicker.getValue());
				rightCircle.setStroke(Color.BLACK);

			}
		});

	}

	@FXML
	protected void handleClearAllButtonAction(ActionEvent event) {
		Item.uid = 0;
		leftSetText.setText("Text");
		rightSetText.setText("Text");
		middleSetText.setText("Text");
		rightGroup.removeAll();
		leftGroup.removeAll();
		matchGroup.removeAll();
		model.getItemList().clear();
		item_list.getItems().clear();
		create_text.requestFocus();
		event.consume();
	}

	@FXML
	protected void handleClearSelectedButtonAction(ActionEvent event) {
		List<Item> copyList = new ArrayList<>(item_list.getSelectionModel().getSelectedItems());
		item_list.getItems().removeAll(copyList);

		leftGroup.removeItems(copyList);
		rightGroup.removeItems(copyList);
		matchGroup.removeItems(copyList);
		leftSetText.setText(leftGroup.toVisualList());
		rightSetText.setText(rightGroup.toVisualList());
		middleSetText.setText(matchGroup.toVisualList());
		removed++;
		model.getItemList().removeAll(copyList);

		if (leftGroup.isEmpty()) {
			leftSetText.setText("Text");
		}
		if (rightGroup.isEmpty()) {
			rightSetText.setText("Text");
		}
		if (matchGroup.isEmpty()) {
			middleSetText.setText("Text");
		}

		create_text.requestFocus();
		event.consume();
	}

	@FXML
	protected void handleItemListDragDetection(MouseEvent event) {
		if (selectedItems.size() <= 0) {
			event.consume();
			return;
		}
		Dragboard db = item_list.startDragAndDrop(TransferMode.COPY_OR_MOVE);
		ClipboardContent cb = new ClipboardContent();
		cb.put(itemFormat, obsListToArrayList(selectedItems));
		db.setContent(cb);
		event.consume();
	}

	@FXML
	protected void handleLeftSetDragOver(DragEvent event) {
		if (event.getGestureSource() != leftSet && event.getDragboard().hasContent(itemFormat)) {
			event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
		}
		event.consume();
	}

	@SuppressWarnings("unchecked")
	@FXML
	protected void handleLeftSetDrop(DragEvent event) {
		boolean isCompleted = false;
		Dragboard db = event.getDragboard();
		if (db.hasContent(itemFormat)) {
			leftGroup.insertItems((ArrayList<Item>) db.getContent(itemFormat));
			Set<Integer> match = leftGroup.findMatching(rightGroup);
			// ArrayList<Item> temp = new ArrayList<Item>();
			for (Item item : itemsContent) {
				if (match.contains(item.getID())) {
					matchGroup.insertItem(item);
					leftGroup.removeItem(item);
					rightGroup.removeItem(item);
				}
			}
			middleSetText.setText(matchGroup.toVisualList());
			leftSetText.setText(leftGroup.toVisualList());
			rightSetText.setText(rightGroup.toVisualList());
			isCompleted = true;
		}
		event.setDropCompleted(isCompleted);
		event.consume();
	}

	@FXML
	protected void handleRightSetDragOver(DragEvent event) {
		if (event.getGestureSource() != leftSet && event.getDragboard().hasContent(itemFormat)) {
			event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
		}
		event.consume();
	}

	@SuppressWarnings("unchecked")
	@FXML
	protected void handleRightSetDrop(DragEvent event) {
		boolean isCompleted = false;
		Dragboard db = event.getDragboard();
		if (db.hasContent(itemFormat)) {
			rightGroup.insertItems((ArrayList<Item>) db.getContent(itemFormat));
			Set<Integer> match = rightGroup.findMatching(leftGroup);
			// ArrayList<Item> temp = new ArrayList<Item>();
			for (Item item : itemsContent) {
				if (match.contains(item.getID())) {
					matchGroup.insertItem(item);
					rightGroup.removeItem(item);
					leftGroup.removeItem(item);
				}
			}
			middleSetText.setText(matchGroup.toVisualList());
			leftSetText.setText(leftGroup.toVisualList());
			rightSetText.setText(rightGroup.toVisualList());
			isCompleted = true;
		}
		event.setDropCompleted(isCompleted);
		event.consume();
	}

}