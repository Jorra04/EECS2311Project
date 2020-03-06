package controller;
import VennDiagram.clearAllAlert;
import VennDiagram.restoreDefaultsAlert;

import java.io.File;
import java.io.IOException;
import java.util.*;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import model.VennModel;
import util.Save;
import model.Group;
import model.Item;
import VennDiagram.TagAlreadyExistsAlert;
import VennDiagram.View;
import VennDiagram.backToMenuAlert;
import VennDiagram.tooManyCirclesAlert;

public class Controller {

	//View.primaryStage.setScene(View.promptWindow); --> code to switch windows.
	// create venn diagram instance
	int removed = 0;
	private static int numCirc = 2;
	private VennModel model;

	ObservableList<Item> itemsContent = FXCollections.observableArrayList();
	ObservableList<Item> selectedItems;
	Group leftGroup;
	Group rightGroup;
	Group matchGroup;
	private static final DataFormat itemFormat = new DataFormat("item");


	
	private ArrayList<Item> itemText = new ArrayList<>();

	
	private ArrayList<Circle> circles = new ArrayList<>();
	// fxml components
	Circle circle;
	@FXML
	Button addCirc;
	@FXML
    private MenuItem newFile;

    @FXML
    private MenuItem openFile;

    @FXML
    private MenuItem saveFile;
    
    @FXML
    private MenuItem switchScene;

    @FXML
    private MenuItem quitProgram;
    @FXML
    private MenuItem restoreDef;
    @FXML
    private MenuItem aboutUs;
	@FXML
	Circle leftCircle;
	@FXML
	Circle rightCircle;
	@FXML
	Button clearSelButton;
	@FXML
	Button identify;
	@FXML
	Button clearData; 

	@FXML
	SplitMenuButton splitMenu = new SplitMenuButton();

	@FXML
	MenuItem lButton = new MenuItem();
	@FXML
	MenuItem rButton = new MenuItem();
	
	MenuItem tButton = new MenuItem();
	
	MenuItem bButton = new MenuItem();
	

	@FXML
	TextField create_text;
	@FXML
	TextField groupIdentifier;
	@FXML
	Button create_button;

	@FXML
	ListView<Item> item_list;
	@FXML
	Pane diagram_pane; // venn diagram is here
	
	@FXML
	StackPane leftSet, rightSet, middleSet;
	@FXML
	Text leftSetText, rightSetText, middleSetText;

	private static ColorPicker colorPicker = new ColorPicker(Color.DODGERBLUE);
	private static VBox box = new VBox(colorPicker);



	// use this to help setup the fxml components, initialize is called as soon as
	// app starts up. Similar to a constructor.
	public void initialize() {
		groupIdentifier.setEditable(false);
		splitMenu.setOnAction(e -> {
			//keep this empty, it basically removes the functionality of the root button in the split
			//button button. Keeps the dropdown functionality.
		});
		
		model = new VennModel();
		clearData.requestFocus();
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
		// methods, they both do the same stuff.
		if (event.getCode().equals(KeyCode.ENTER)) {
			createData();
		}
		event.consume();
	}

	@FXML
	protected void handleCreateButtonAction(ActionEvent event) {
		createData();
		event.consume();
	}

	@FXML
	protected void menuClick() {
		splitMenu.getItems().add(lButton);
		splitMenu.getItems().add(rButton);
		
	}

	@SuppressWarnings("unchecked")
	@FXML
	protected void leftCircleColour() {
		if (diagram_pane.getChildren().contains(Controller.box)) {
			diagram_pane.getChildren().remove(Controller.box);
			Color colorVal = (Color)leftCircle.getFill();
			colorPicker.setValue(colorVal);
		}
		splitMenu.setText("Left Circle");

		diagram_pane.getChildren().add(Controller.box);
		box.setLayoutX(splitMenu.getLayoutX());
		box.setLayoutY(splitMenu.getLayoutY() - 40);

		colorPicker.setOnAction(new EventHandler() {
			@Override
			public void handle(javafx.event.Event event) {
				leftCircle.setFill(colorPicker.getValue());
				leftCircle.setStroke(Color.BLACK);
				
			}
		});

	}

	// to change the color of the right circle;
	@SuppressWarnings("unchecked")
	@FXML
	protected void rightCircleColour() {
		if (diagram_pane.getChildren().contains(Controller.box)) {
			diagram_pane.getChildren().remove(Controller.box);
			Color colorVal = (Color)rightCircle.getFill();
			colorPicker.setValue(colorVal);
		}
		splitMenu.setText("Right Circle");

		diagram_pane.getChildren().add(Controller.box);
		box.setLayoutX(splitMenu.getLayoutX());
		box.setLayoutY(splitMenu.getLayoutY() -40);
		colorPicker.setOnAction(new EventHandler() {
			public void handle(javafx.event.Event event) {
				rightCircle.setFill(colorPicker.getValue());
				rightCircle.setStroke(Color.BLACK);

			}
		});

	}

	@FXML
	protected void handleClearAllButtonAction(ActionEvent event) {
		//gives a popup that the user is about to delete all info.
		if(!model.getItemList().isEmpty()) {
			clearAllAlert.display("ALERT", "You are about to delete all data, do you wish to proceed?");
			if(clearAllAlert.closePressed) {
				remover();
			}
		}
		
		event.consume();
		
		
		
	}

	@FXML
	protected void handleClearSelectedButtonAction(ActionEvent event) {
		//the following line takes the selected items and makes them into a list.
		List<Item> copyList = new ArrayList<>(item_list.getSelectionModel().getSelectedItems());
		item_list.getItems().removeAll(copyList);
		//remove what is selected.
		leftGroup.removeItems(copyList);
		rightGroup.removeItems(copyList);
		matchGroup.removeItems(copyList);
		//reset the group text accordingly.
		leftSetText.setText(leftGroup.toVisualList());
		rightSetText.setText(rightGroup.toVisualList());
		middleSetText.setText(matchGroup.toVisualList());
		groupIdentifier.clear();
		removed++; // increment the number of removed items.
		model.getItemList().removeAll(copyList); //update the item list
		itemText.removeAll(copyList); // update the checker list.
		

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
//			setMatcher(rightGroupList,leftGroupList,midGroupList,arr);

			
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
//			setMatcher(rightGroupList,leftGroupList,midGroupList,arr);
			
			isCompleted = true;
		}
		
		event.setDropCompleted(isCompleted);
		event.consume();
	}
	
	@FXML
	protected void menuButtonClose(ActionEvent event) {
		VennDiagram.quitProgramAlert.display("Confirm Exit", "Are you sure you want to exit?");
		if(VennDiagram.quitProgramAlert.closePressed) {
			View.primaryStage.close();
		}
		event.consume();
		
	}
	
	@FXML 
	protected void aboutUs(ActionEvent event) {
		//Whole thing is a try catch, it tries to find the location of the embedded file.
		int FileNotFoundCount = 0;
		try {

			File pdfFile = new File("EECS2311Project\\resources\\userMan\\manual.pdf");
			if (pdfFile.exists()) {

				if (java.awt.Desktop.isDesktopSupported()) {
					java.awt.Desktop.getDesktop().open(pdfFile);
				} else {
					System.out.println("Awt Desktop is not supported!");
				}
			}
			else {
				FileNotFoundCount++;
			}
		  } catch (Exception ex) {
			ex.printStackTrace();
		  }
		
		try {

			File pdfFile = new File("src\\main\\resources\\userMan\\manual.pdf");
			if (pdfFile.exists()) {

				if (java.awt.Desktop.isDesktopSupported()) {
					java.awt.Desktop.getDesktop().open(pdfFile);
				} else {
					System.out.println("Awt Desktop is not supported!");
				}
			} else {
				FileNotFoundCount++;
			}
		  } catch (Exception ex) {
			ex.printStackTrace();
		  }
		if(FileNotFoundCount > 1 ) {
			System.out.println(FileNotFoundCount);
			System.out.println("File Not Found");
		}
		
		event.consume();
	}
	
	protected boolean tagAlreadyExists(String tag) {
		String checker = tag.replaceAll(" ", ""); //strip the item of its white space
		//		System.out.println(itemText);
		for(Item item : itemText) {
			if(item.getText().equals(checker)) {
				return true;
			}
		}
		return false;
	}
	
	@FXML
	public void addCirc(ActionEvent event) {
		if(Controller.numCirc == 2) { // if the numCircles is 2, then we put the 3rd in a specific place.
			
			circleCreator(leftCircle.getRadius(),280,400);
			numCirc++; // inc the number of circles.
		}
		else if(Controller.numCirc == 3) {// if the numCircles is 3,then we put the 4th in a specific place.
			
			circleCreator(leftCircle.getRadius(),280,200);
			numCirc++; // inc the num of cirlces.

		}
		else {
			// >4 cicles is not allowed, this will check any circle creation beyond 4.
			tooManyCirclesAlert.display("Exceeded Number of Allowed Circles!", "You have exceeded the "
					+ "number of allowed cirlces.");
		}
	}
	
	private void circleCreator(double radius, int startX, int startY) {
		//making the circles
		circle = new Circle();
		circle.setFill(Color.DODGERBLUE);
		circle.setOpacity(leftCircle.getOpacity());
		circle.setRadius(radius);
		circle.setLayoutX(startX);
		circle.setLayoutY(startY);
		circles.add(circle);
		diagram_pane.getChildren().add(circle);
		
	}
	
	private void circleDestroyer(List<Circle> circle) {
		//delete the circles.
		diagram_pane.getChildren().removeAll(circles);
	}
	
	@FXML
	protected void backToMenu(ActionEvent event) {
		//Prompt asking if the user wants to go back to the menu.
		//Looks at which button is pressed, makes its decision based on that.
		backToMenuAlert.display("Alert", "Back to menu?");
		if(backToMenuAlert.confirmPressed) {
			View.primaryStage.setScene(View.promptWindow);
		}
	}
	
	@FXML
	protected void restoreDefault(ActionEvent event) {
		restoreDefaultsAlert.display("Restore Settings", "Restore settings to their factory default?");
		if(restoreDefaultsAlert.deletePressed) {
			remover(); // calls fucntion that removes all.
			circleDestroyer(this.circles); //removes additional circles
			Controller.numCirc = 2; // resets the value of numCirc, allows for the other functions to work.
			//the rest is resetting original vals.
			leftCircle.setFill(Color.DODGERBLUE);
			rightCircle.setFill(Color.DODGERBLUE);
			Color colorVal = (Color)rightCircle.getFill();
			colorPicker.setValue(colorVal);
			event.consume();
		}
		
	}
	
	private void remover() { //function to remove all items, used in multiple places.
		//setting the text of each group to its default value.
		leftSetText.setText("Text");
		rightSetText.setText("Text");
		middleSetText.setText("Text");
		//removing from the groups.
		rightGroup.removeAll();
		leftGroup.removeAll();
		matchGroup.removeAll();
		model.getItemList().clear();
		item_list.getItems().clear(); // clearing the listview.
		groupIdentifier.clear(); // clearing the group identifiers.
		create_text.requestFocus();
		itemText.clear(); //this is needed, as if we don't have this, the program thinks we have duplicate items present.
		Item.uid = 0;
		diagram_pane.getChildren().remove(Controller.box); // remove the extra items.
		clearAllAlert.cancelPressed = false;
		clearAllAlert.closePressed = false;
		
	}
	private void createData() {
		if (create_text.getLength() != 0) {
			if(!tagAlreadyExists(create_text.getText())) {
				Item item = new Item(create_text.getText());
				String adder = create_text.getText().replaceAll(" ", "");
				itemText.add(new Item(adder)); //Look for a way around this, i feel like there is potential for error here.
				Item.uid--; //correcting for the new item made.
				model.getItemList().add(item);
				itemsContent.setAll(model.getItemList());
			}
			else {
				TagAlreadyExistsAlert.display("Alert", "Tag Already Exists!"); 
			}
			
			create_text.clear(); //reset textfield
			create_text.requestFocus(); //get the textfield to listen for the next input.
			
			
		}
	}
	@FXML
	protected void refactor(ActionEvent event)throws Exception {
		List<Item> copyList = new ArrayList<>(item_list.getSelectionModel().getSelectedItems());
		if(copyList.size() == 0) { // no selected items.
			VennDiagram.TagAlreadyExistsAlert.display("ERROR", "Select some items before trying to rename them.");
			return;
		}
		
		if(copyList.size() > 1) { // more than 1 selected items, shouldn't be able to do this.
			VennDiagram.TagAlreadyExistsAlert.display("Too Many Items", "You've selected more than one item to refactor!");
			/*
			 * using "return" in the void method, means we dont have to reset
			 * the values of copylist, because it's a local variable and gets reinitialized every
			 * time the method is called.
			 */
			return;
		}
		
		
		VennDiagram.refactorWindow.display("Window");
		if(!controller.refactorController.buttonPressed) { //checks if the exit button is pressed
			//or if the refactor button is pressed.
			return;
		}
		
		if(tagAlreadyExists(controller.refactorController.text)) { //stops duplicates.
			VennDiagram.TagAlreadyExistsAlert.display("ERROR", "Tag Already Exists");
			return;
		}
		
		for(Item item : copyList) {
			item.setText(controller.refactorController.text); //set the text.
		}
		for(Item item: itemText) {
			item.setText(controller.refactorController.text); // set the text in the checket list.
		}
		System.out.println(model.getItemList());
		item_list.refresh(); //refresh the listView to show us the current values.
	}
	
	@FXML
	protected void checker(ActionEvent e) {
	
	}
	
	
	protected String groupFinder(Item item) {
		Set<String> left = leftGroup.toSet();
		Set<String> right = rightGroup.toSet();
		Set<String> mid = matchGroup.toSet();
		if(mid.contains(item.text)) {
			return "Middle Set";
		}
		if(left.contains(item.text)) {
			return "Left Set";
		}
		if(right.contains(item.text)) {
			return "Right Set";
		}
		return "Not Assigned";
	}

	
	
	@FXML
	protected void identifyGroup(ActionEvent event) {
		List<Item> copyList = new ArrayList<>(item_list.getSelectionModel().getSelectedItems());
		for(Item item :copyList) {
			groupIdentifier.setText(item.text+ ": " + groupFinder(item));
			if(groupFinder(item).equals("Left Set")) {
				groupIdentifier.setStyle("-fx-text-fill: green;");
			}
			else if(groupFinder(item).equals("Right Set")) {
				groupIdentifier.setStyle("-fx-text-fill: red;");
			}
			else if(groupFinder(item).equals("Middle Set")) {
				groupIdentifier.setStyle("-fx-text-fill: blue;");
			}
			else {
				groupIdentifier.setStyle("-fx-text-fill: black;");
			}
			
		}
	}

	@FXML
	protected void handleSaveMenuButton(ActionEvent event) {
		if(!item_list.getItems().isEmpty()) {
			System.out.println("writing to excel");
			try {
				Save.writeExcel(model);
			} catch (InvalidFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			TagAlreadyExistsAlert.display("Save", "Make Changes Before Saving!");
		}
		
	}
}