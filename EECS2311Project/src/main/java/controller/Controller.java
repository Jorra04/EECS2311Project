package controller;

import VennDiagram.clearAllAlert;
import VennDiagram.restoreDefaultsAlert;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import model.VennModel;
import util.Save;
import model.Group;
import model.Item;
import model.Shaker;
import VennDiagram.TagAlreadyExistsAlert;
import VennDiagram.View;
import VennDiagram.backToMenuAlert;
import VennDiagram.tooManyCirclesAlert;

public class Controller {
	ContextMenu contextMenu = new ContextMenu();
	MenuItem delete = new MenuItem("Delete");
	MenuItem refactor = new MenuItem("Refactor");
	@FXML
	Slider circleSize;
	@FXML
	Slider leftCircleSlider;
	@FXML
	Slider rightCircleSlider;
	boolean animationDone = false;
	// View.primaryStage.setScene(View.promptWindow); --> code to switch windows.
	// create venn diagram instance
	private static int numCirc = 2;
	public List<String> containsArray = new ArrayList<>();
	// -----------------for tracking diagram_pane's
	// dimensions.--------------------------------------------------------
	double paneX;
	double paneY;
	// -----------------draggable item
	// variables------------------------------------------------------------------------
	double spaceY = 50;
	double spaceX = 100;
	public static VennModel model;

	static ObservableList<Item> itemsContent = FXCollections.observableArrayList();
	ObservableSet<Item> test = FXCollections.observableSet();
	ObservableList<Item> selectedItems;

	Group leftGroup;

	Group rightGroup;
	Group matchGroup;
	
	private static final DataFormat itemFormat = new DataFormat("item");
//	private static final String DEFAULT_CONTROL_INNER_BACKGROUND = "derive(red,80%)";
//    private static final String HIGHLIGHTED_CONTROL_INNER_BACKGROUND = "derive(palegreen, 50%)";

	protected static TreeSet<String> loadList = new TreeSet<>();

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
	Button createDraggableItemButton;
	@FXML
	ToolBar toolbar;

	@FXML
	SplitMenuButton splitMenu = new SplitMenuButton();

	@FXML
	MenuItem lButton = new MenuItem();
	@FXML
	MenuItem rButton = new MenuItem();

	MenuItem tButton = new MenuItem();

	MenuItem bButton = new MenuItem("Bottom Circle");

	@FXML
	TextField create_text;
	@FXML
	TextField groupIdentifier;
	@FXML
	Button create_button;
//	@FXML
//	ListView<Item> leftGroupList;
//	@FXML
//	ListView<Item> rightGroupList;
//	@FXML
//	ListView<Item> midGroupList;
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

	public boolean isItemClicked; // Returns a true or false if the item has been clicked or not
	public static Item clickedItem; // Holds the item object that is currently clicked
	double leftCircOrig;
	double rightCircleOrig;
	double origRad;
	double runsumLeft;
	double runSumRight;
	
	//new stuff i added 04-04
	boolean threeCircs = false;
	Circle bottomCircle = new Circle();
	// use this to help setup the fxml components, initialize is called as soon as
	// app starts up. Similar to a constructor.
	public void initialize() {
		
		
		diagram_pane.setStyle("-fx-background-color: #F5F5DC");
		item_list.setStyle("-fx-background-color: #F5F5DC");
		bottomCircle.setRadius(leftCircle.getRadius());
		bottomCircle.setCenterX(575);
		bottomCircle.setCenterY(700);
		bottomCircle.setOpacity(leftCircle.getOpacity());
		bottomCircle.setFill(leftCircle.getFill());
		contextMenu.getItems().addAll(refactor, delete);
		// create listeners on the diagram_pane's dimensions so its components can
		// resize as well
		diagram_pane.widthProperty().addListener((obs, oldVal, newVal) -> {
			paneX = newVal.doubleValue();
			leftCircle.setLayoutX(paneX / 3);
			// move right circle to the right for intersection
			rightCircle.setLayoutX(paneX / 3 + paneX / 4);
		});
		diagram_pane.heightProperty().addListener((obs, oldVal, newVal) -> {
			paneY = newVal.doubleValue();
			leftCircle.setLayoutY(paneY / 2);
			rightCircle.setLayoutY(paneY / 2);
		});

		groupIdentifier.setEditable(false);
		splitMenu.setOnAction(e -> {
			// keep this empty, it basically removes the functionality of the root button in
			// the split
			// button button. Keeps the dropdown functionality.
		});

		model = new VennModel();
		clearData.requestFocus();
		// setup content list, item_list reflects the observable list itemsContent
		item_list.setItems(itemsContent); // itemsContent reflects the changes.
		// item_list allow multiple selection
		item_list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		selectedItems = item_list.getSelectionModel().getSelectedItems();

		// setup 2 groups
		model.createGroup("left");
		model.createGroup("right");
		model.createGroup("match");
		leftGroup = model.getGroupMap().get("left");
		rightGroup = model.getGroupMap().get("right");
		matchGroup = model.getGroupMap().get("match");

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
				if(getIndex() %2 == 0) {
					setStyle("-fx-background-color: #F5F5DC");
				}
				else {
					setStyle("-fx-background-color: #F5EAB5");
				}
				
			}
		});

		origRad = leftCircle.getRadius();
		leftCircOrig = leftCircle.getBoundsInParent().getCenterX();
		rightCircleOrig = rightCircle.getBoundsInParent().getCenterX();

		/*
		 * resizing circles.
		 */
		circleSize.setRotate(180);
		circleSize.setValue(366);

		circleSize.valueProperty().addListener((ChangeListener<? super Number>) new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				leftCircle.setRadius((double) newValue);
				rightCircle.setRadius((double) newValue);

				leftCircleSlider.setValue(0);
				rightCircleSlider.setValue(rightCircleSlider.getMax());
			}
		});
//		
//		
		leftCircleSlider.valueProperty().addListener((ChangeListener<? super Number>) new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//				System.out.println(oldValue);
				leftCircle.setCenterX((double) newValue);

			}
		});

		rightCircleSlider.valueProperty().addListener((ChangeListener<? super Number>) new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//				System.out.println(oldValue);
				rightCircle.setCenterX((double) newValue);

			}
		});

		/*
		 * installing tooltips on the buttons so the user can see exactly what they do.
		 */
		Tooltip tt1 = new Tooltip("Create a diagram item that can be added to a set.");
		Tooltip tt2 = new Tooltip("Change to colour of either sets.");
		Tooltip tt3 = new Tooltip("Add an additional circle (Up to 4 circles are supported).");
		Tooltip tt4 = new Tooltip("Clear all unsaved data");
		Tooltip tt5 = new Tooltip(
				"Choose which elements you would like to delete (Items are chosen from the word bank).");
		Tooltip tt6 = new Tooltip("Adjust the radius of the sets to suit your project!");
		Tooltip tt7 = new Tooltip("Adjust the placement of the left set to suit your project.");
		Tooltip tt8 = new Tooltip("Adjust the placement of the right set to suit your project.");
		Tooltip tt9 = new Tooltip("Enter text to populate the word bank.");
		Tooltip tt10 = new Tooltip("Identify which set an element belongs to.");
		Tooltip.install(createDraggableItemButton, tt1);
		Tooltip.install(splitMenu, tt2);
		Tooltip.install(addCirc, tt3);
		Tooltip.install(clearData, tt4);
		Tooltip.install(clearSelButton, tt5);
		Tooltip.install(circleSize, tt6);
		Tooltip.install(leftCircleSlider, tt7);
		Tooltip.install(rightCircleSlider, tt8);
		Tooltip.install(create_button, tt9);
		Tooltip.install(identify, tt10);
		tt1.setShowDelay(Duration.millis(500));
		tt2.setShowDelay(Duration.millis(500));
		tt3.setShowDelay(Duration.millis(500));
		tt4.setShowDelay(Duration.millis(500));
		tt5.setShowDelay(Duration.millis(500));
		tt6.setShowDelay(Duration.millis(500));
		tt7.setShowDelay(Duration.millis(500));
		tt8.setShowDelay(Duration.millis(500));
		tt9.setShowDelay(Duration.millis(500));
		tt10.setShowDelay(Duration.millis(500));
//		rightCircle.setOnMouseClicked(new EventHandler<MouseEvent>() {
//			@SuppressWarnings("unchecked")
//			@Override
//			public void handle(MouseEvent event) {
//				if(event.getButton().equals(MouseButton.SECONDARY)) {
//					System.out.println("heeee");
//				}
//			}
//		});
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
		if (toolbar.getItems().contains(Controller.box)) {
			toolbar.getItems().remove(Controller.box);
			Color colorVal = (Color) leftCircle.getFill();
			colorPicker.setValue(colorVal);
		}
		splitMenu.setText("Left Circle");
		toolbar.getItems().add(Controller.box);
//		diagram_pane.getChildren().add(Controller.box);
//		box.setLayoutX(splitMenu.getLayoutX() + 360);
//		box.setLayoutY(splitMenu.getLayoutY() - 37.5);

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
		if (toolbar.getItems().contains(Controller.box)) {
			toolbar.getItems().remove(Controller.box);
			Color colorVal = (Color) rightCircle.getFill();
			colorPicker.setValue(colorVal);
		}
		splitMenu.setText("Right Circle");
		toolbar.getItems().add(Controller.box);

//		diagram_pane.getChildren().add(Controller.box);
//		box.setPrefSize(splitMenu.getPrefWidth(), splitMenu.getPrefHeight());
//		box.setLayoutX(splitMenu.getLayoutX() + 360);
//		box.setLayoutY(splitMenu.getLayoutY() - 37.5);
		colorPicker.setOnAction(new EventHandler() {
			public void handle(javafx.event.Event event) {
				rightCircle.setFill(colorPicker.getValue());
				rightCircle.setStroke(Color.BLACK);

			}
		});

	}
	@SuppressWarnings("unchecked")
	@FXML
	protected void bottomCircleColour() {
		if (toolbar.getItems().contains(Controller.box)) {
			toolbar.getItems().remove(Controller.box);
			Color colorVal = (Color) bottomCircle.getFill();
			colorPicker.setValue(colorVal);
		}
		splitMenu.setText("Bottom Circle");
		toolbar.getItems().add(Controller.box);

		colorPicker.setOnAction(new EventHandler() {
			@Override
			public void handle(javafx.event.Event event) {
				bottomCircle.setFill(colorPicker.getValue());
				bottomCircle.setStroke(Color.BLACK);

			}
		});

	}

	@FXML
	protected void handleClearAllButtonAction(ActionEvent event) {
		// gives a popup that the user is about to delete all info.
		if (model.size() != 0) {
			clearAllAlert.display("ALERT", "You are about to delete all data, do you wish to proceed?");
			if (clearAllAlert.closePressed) {
				remover();
			}
		}
		event.consume();
	}

	/*
	 * New Method: Group objects are stored in an array which is looped over.
	 * .contains(clickedItem) checks the TreeMap of the group at index "i" for a
	 * matching key O(1) if(.contains(clickedItem)) returns true then the item will
	 * removed from the listview and the group
	 */
	@FXML
	protected void handleClearSelectedButtonAction(ActionEvent event) {

		Group groupArray[] = new Group[3];
		groupArray[0] = leftGroup;
		groupArray[1] = rightGroup;
		groupArray[2] = matchGroup;

		for (int i = 0; i < 3; i++) {
			if (groupArray[i].contains(clickedItem)) {
				groupArray[i].removeItem(clickedItem);
				item_list.getItems().remove(clickedItem);
			}
		}
		item_list.refresh();
		leftSetText.setText(leftGroup.toVisualList());
		rightSetText.setText(rightGroup.toVisualList());
		middleSetText.setText(matchGroup.toVisualList());
		groupIdentifier.clear();
		event.consume();
	}

	/*
	 * New Method: testMouse() detects if an item is clicked and what that item
	 * object is isItemClicked: returns true if an item is clicked clickedItem:
	 * returns the Item object associated with the click
	 */
	@FXML
	public boolean testMouse(MouseEvent event) {
		isItemClicked = item_list.isFocused();
		clickedItem = item_list.getFocusModel().getFocusedItem();
		event.consume();
		return isItemClicked;
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

			event.getSceneX();
			event.getSceneY();
//			middleSetText.setText(matchGroup.toVisualList());
//			leftSetText.setText(leftGroup.toVisualList());
//			rightSetText.setText(rightGroup.toVisualList());
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

			isCompleted = true;
		}

		event.setDropCompleted(isCompleted);
		event.consume();
	}

	@FXML
	protected void menuButtonClose(ActionEvent event) {
		VennDiagram.quitProgramAlert.display("Confirm Exit", "Are you sure you want to exit?");
		if (VennDiagram.quitProgramAlert.closePressed) {
			View.primaryStage.close();
		}
		event.consume();

	}

	@FXML
	protected void aboutUs(ActionEvent event) {
		// Whole thing is a try catch, it tries to find the location of the embedded
		// file.
		int FileNotFoundCount = 0;
		try {

			File pdfFile = new File("EECS2311Project\\resources\\userMan\\manual.pdf");
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
		if (FileNotFoundCount > 1) {
			System.out.println(FileNotFoundCount);
			System.out.println("File Not Found");
		}

		event.consume();
	}

	@FXML
	public void handleCreateDraggableItemButton(ActionEvent event) {
		// if the selected items in the item list is 0, dont do anything
		if (selectedItems.size() <= 0) {
			event.consume();
			return;
		}
		double itemPositionY = 0;
		double itemPositionX = 0;
		for (Item item : selectedItems) {
			// provide each item with a reference to the controller class itself, so it can
			// compare the item with the circle positions for intersection.
			DraggableItem tempItem = new DraggableItem(item, this);
			tempItem.getLabel().setTextFill(Color.BLACK); // tbh it looks gray but w.e.
			tempItem.getLabel().setFont(new Font("Arial", 18));
			tempItem.setPrefSize(Region.USE_COMPUTED_SIZE + 50, Region.USE_COMPUTED_SIZE + 50);
			// set position within diagram pane
			itemPositionY += spaceY;
			if (itemPositionY >= paneY - 100) {
				itemPositionX += spaceX; // if current "column" goes past the diagram dimensions, go to the next
											// "column"
				itemPositionY = 0 + spaceY;
			}
			tempItem.setLayoutX(itemPositionX); // set to the left
			tempItem.setLayoutY(itemPositionY); // space between each item

			if (!containsArray.contains(tempItem.getItem().getText())
					|| VennDiagram.repeatDraggableItem.checkboxPressed) {
				diagram_pane.getChildren().add(tempItem);
				containsArray.add(tempItem.getItem().getText());

			} else {
				VennDiagram.repeatDraggableItem.display("Alert", "Diagram Item Already Exists.");
			}

			tempItem.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@SuppressWarnings("unchecked")
				@Override
				public void handle(MouseEvent event) {
					try {
						if (event.getButton().equals(MouseButton.SECONDARY)) {
							/*
							 * warn the user before. but this is a rough implementation.
							 */
							contextMenu.show(View.primaryStage, event.getScreenX(), event.getScreenY());
							contextMenu.getItems().get(0).setOnAction(e -> {
								try {
									int index = containsArray.indexOf(tempItem.getItem().getText());
									controller.refactorController.text = tempItem.item.getText();
									controller.refactorController.description = tempItem.getDescription();
									controller.refactorController.color = (Color) tempItem.getLabel().getTextFill();
									VennDiagram.refactorWindow.display("refactor");
									if (controller.refactorController.buttonPressed) {
										tempItem.text.setText(controller.refactorController.text);
										tempItem.item.setText(controller.refactorController.text);
										tempItem.tooltip.setText(controller.refactorController.description);
										// need to loop through elements and change the element to whatever we changed
										// it to in the refactor.
										tempItem.getLabel().setTextFill(controller.refactorController.color);
										containsArray.set(index, tempItem.getItem().getText());
										item_list.refresh();
									}

								} catch (Exception e1) {

								}
							});

							contextMenu.getItems().get(1).setOnAction(e -> {
								try {

//									System.out.println(model.getItemSet());
									model.getItemSet().remove(tempItem.getItem());
									int index = containsArray.indexOf(tempItem.getItem().getText());
									containsArray.remove(index);
									item_list.getItems().remove(tempItem.item);

									FadeTransition ft = new FadeTransition(Duration.millis(1000), tempItem);

									ft.setFromValue(1.0);
									ft.setToValue(0);
									ft.setAutoReverse(true);

									ft.play();
									ft.setOnFinished(e1 -> {
										diagram_pane.getChildren().remove(tempItem);
									});

//									System.out.println(model.getItemSet());
//									System.out.println(itemsContent);
//									System.out.println(containsArray);
									groupIdentifier.clear(); //clears the group identifier.
									item_list.refresh();
								} catch (Exception e1) {

								}
							});

						}
					} catch (Exception e) {

					}

				}
			});
			tempItem.setOnMouseReleased(e->{
//				System.out.println("("+tempItem.getX() + "," + tempItem.getY()+")");
				groupFinder2(tempItem);
			});
//			tempItem.setOnMouseEntered(e->{
//				tempItem.setScaleX(1.05);
//				tempItem.setScaleY(1.05);
//				tempItem.setScaleZ(1.05);
//				
//				
//				
//			});
//			tempItem.setOnMouseExited(e->{
//				tempItem.setScaleX(1);
//				tempItem.setScaleY(1);
//				tempItem.setScaleZ(1);
//			});
		}

		event.consume();
	}

	@FXML
	public void addCirc(ActionEvent event) {
		if (Controller.numCirc == 2) { // if the numCircles is 2, then we put the 3rd in a specific place.
			circleCreator(leftCircle.getRadius(), 300, 400);
			numCirc++; // inc the number of circles.
		} else if (Controller.numCirc == 3) {// if the numCircles is 3,then we put the 4th in a specific place.

			circleCreator(leftCircle.getRadius(), 300, 400);
			numCirc++; // inc the num of cirlces.

		} else {
			// >4 cicles is not allowed, this will check any circle creation beyond 4.
			tooManyCirclesAlert.display("Exceeded Number of Allowed Circles!",
					"You have exceeded the " + "number of allowed cirlces.");
		}
	}

	private void circleCreator(double radius, int startX, int startY) {
		// making the circles
//		circle = new Circle();
//		circle.setFill(Color.DODGERBLUE);
//		circle.setOpacity(leftCircle.getOpacity());
//		circle.setRadius(radius);
//		circle.setLayoutX(startX);
//		circle.setLayoutY(startY);
//		circles.add(circle);
//		diagram_pane.getChildren().add(circle);
		if(threeCircs) {
			return;
		}
		threeCircs = true;
		splitMenu.getItems().add(bButton);
		bottomCircle.setOpacity(leftCircle.getOpacity());
		diagram_pane.getChildren().add(0,bottomCircle);
		
		circles.add(bottomCircle);
	}

	private void circleDestroyer(List<Circle> circle) {
		// delete the circles.
		diagram_pane.getChildren().removeAll(circle);
	}

	@FXML
	protected void backToMenu(ActionEvent event) {
		// Prompt asking if the user wants to go back to the menu.
		// Looks at which button is pressed, makes its decision based on that.
		backToMenuAlert.display("Alert", "Back to menu?");
		if (backToMenuAlert.confirmPressed) {
			startPageController.load = false; // Ensures the Controller knows that we are going back to the beginning.
			startPageController.selectedFile = null; // Make the userFile Load to null
			remover();
			View.primaryStage.setScene(View.promptWindow);
		}
	}

	@FXML
	protected void restoreDefault(ActionEvent event) {
		restoreDefaultsAlert.display("Restore Settings", "Restore settings to their factory default?");
		if (restoreDefaultsAlert.deletePressed) {
			remover(); // calls fucntion that removes all.
			/*
			 * add fade animation on circles.
			 */
			if(threeCircs) {
				FadeTransition ft = new FadeTransition(Duration.millis(1000), bottomCircle);

				ft.setFromValue(bottomCircle.getOpacity());
				ft.setToValue(0);
				ft.setAutoReverse(true);

				ft.play();
				ft.setOnFinished(e1 -> {
					circleDestroyer(this.circles);
				});
			}
			 // removes additional circles
			Controller.numCirc = 2; // resets the value of numCirc, allows for the other functions to work.
			// the rest is resetting original vals.
			leftCircle.setFill(Color.DODGERBLUE);
			rightCircle.setFill(Color.DODGERBLUE);
			Color colorVal = (Color) rightCircle.getFill();
			colorPicker.setValue(colorVal);
			rightCircle.setRadius(origRad);
			leftCircle.setRadius(origRad);
			threeCircs = false;
			event.consume();
		}

	}

	private void remover() { // function to remove all items, used in multiple places.
		// removing from the groups.
		rightGroup.removeAll();
		leftGroup.removeAll();
		matchGroup.removeAll();
		model.getItemSet().clear();
		item_list.getItems().clear(); // clearing the listview.
		groupIdentifier.clear(); // clearing the group identifiers.
		create_text.requestFocus();
		diagram_pane.getChildren().remove(Controller.box); // remove the extra items.
		model.getItemSet().clear();
		containsArray.clear();
		clearAllAlert.cancelPressed = false;
		clearAllAlert.closePressed = false;
		/*
		 * i know that this method is like really inefficient, but it does what i want
		 * it to do.
		 */
		RotateTransition rt = new RotateTransition(Duration.millis(200), leftCircle);
		RotateTransition rt2 = new RotateTransition(Duration.millis(200), rightCircle);
		rt.setAxis(Rotate.Y_AXIS);
		rt.setByAngle(360);
		rt.setCycleCount(8);
		rt.setAutoReverse(true);
		rt.play();
		rt2.setAxis(Rotate.Y_AXIS);
		rt2.setByAngle(360);
		rt2.setCycleCount(8);
		rt2.setAutoReverse(true);
		rt2.play();
		if(threeCircs) {
			RotateTransition rt3 = new RotateTransition(Duration.millis(200), bottomCircle);
			rt3.setAxis(Rotate.X_AXIS);
			rt3.setByAngle(360);
			rt3.setCycleCount(8);
			rt3.setAutoReverse(true);
			rt3.play();
		}
		FadeTransition ft = new FadeTransition();
		for (Node item : diagram_pane.getChildren()) {
			if (item.getClass().equals(DraggableItem.class)) {
				ft = new FadeTransition(Duration.millis(1000), item);
				ft.setFromValue(1.0);
				ft.setToValue(0);
				ft.setAutoReverse(true);
				ft.play();
			}

		}
		ft.setOnFinished(e -> {
			for (Iterator<Node> iterator = diagram_pane.getChildren().iterator(); iterator.hasNext();) {
				if (iterator.next() instanceof DraggableItem) {
					iterator.remove();
				}
			}
		});
		
		

	}

	/*
	 * New Method: creates the data that will be populated by the ListView. If the
	 * model contains the item being added an alert is popped O(nlogn). Note that no
	 * error detection is required since the data structure is a set and repeated
	 * values are not allowed. ****Feel free to optimize this with ID's for O(1)
	 * 
	 * else the model gets the item. addSet() adds the variable to the set structure
	 * and .add() adds the item to the listview.
	 */
	protected void createData() {
		if (create_text.getLength() != 0) {
			Item testing = new Item(create_text.getText().trim());
			if (model.containsText(testing.text)) {
				TagAlreadyExistsAlert.display("Alert", "Tag Already Exists!");
			} else {
				model.addSet(testing);
				itemsContent.add(testing);
				create_text.clear(); // reset textfield
				create_text.requestFocus(); // get the textfield to listen for the next input.
			}
		}
	}

	/*
	 * New Method: checks the model for element by string O(1).
	 * 
	 */
	protected static void loadData(String st) {
		// Need To Add To the Set List
		Item item = new Item(st);
		model.addSet(item);
		itemsContent.add(item);
	}

	@FXML
	protected void refactor(ActionEvent event) throws Exception {
		if (isItemClicked == false) { // no selected items.
			VennDiagram.TagAlreadyExistsAlert.display("ERROR", "Select some items before trying to rename them.");
		}

		else {
			VennDiagram.refactorWindow.display("Window");
			if (!controller.refactorController.buttonPressed) { // checks if the exit button is pressed
				// or if the refactor button is pressed.
				return;
			}
			if (model.containsText(refactorController.text)) { // stops duplicates.
				VennDiagram.TagAlreadyExistsAlert.display("ERROR", "Tag Already Exists");
				return;
			} else {
				clickedItem.text = refactorController.text;
			}
			item_list.refresh(); // refresh the listView to show us the current values.
		}
	}

	protected String groupFinder(Item item) {
		Set<String> left = leftGroup.toSet();
		Set<String> right = rightGroup.toSet();
		Set<String> mid = matchGroup.toSet();
		if (mid.contains(item.text)) {
			return "Middle Set";
		}
		if (left.contains(item.text)) {
			return "Left Set";
		}
		if (right.contains(item.text)) {
			return "Right Set";
		}
		return "Not Assigned";
	}

	@FXML
	protected void identifyGroup(ActionEvent event) {
		List<Item> copyList = new ArrayList<>(item_list.getSelectionModel().getSelectedItems());
		for (Item item : copyList) {
			groupIdentifier.setText(item.text + ": " + groupFinder(item));
			if (groupFinder(item).equals("Left Set")) {
				groupIdentifier.setStyle("-fx-text-fill: green;");
			} else if (groupFinder(item).equals("Right Set")) {
				groupIdentifier.setStyle("-fx-text-fill: red;");
			} else if (groupFinder(item).equals("Middle Set")) {
				groupIdentifier.setStyle("-fx-text-fill: blue;");
			} else {
				groupIdentifier.setStyle("-fx-text-fill: black;");
			}

		}
	}

	@FXML
	protected void handleSaveMenuButton(ActionEvent event) {
		if (!item_list.getItems().isEmpty()) {
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
		} else {
			TagAlreadyExistsAlert.display("Save", "Make Changes Before Saving!");
		}

	}
	
	protected void groupFinder2(DraggableItem item) {
		double leftCircleDist;
		double lval1;
		double rval1;
		double lval2;
		double  rval2;
		double lsqrtVal1;
		double rsqrtVal1;
		double lsqrtVal2;
		double rsqrtVal2;
		double rightCircleDist;
		
		double bval1;
		double bval2;
		double bsqrtVal1;
		double bsqrtVal2;
		double bottomCircleDist;
		if(!threeCircs) {
			leftCircleDist = 0;
			lval1 = item.getX() - leftCircle.getBoundsInParent().getCenterX();
			lval2 = item.getY() - leftCircle.getBoundsInParent().getCenterY();
			lsqrtVal1 =  Math.pow(lval1, 2);
			lsqrtVal2 =  Math.pow(lval2, 2);
			leftCircleDist = Math.sqrt(lsqrtVal1 + lsqrtVal2);
			
			rightCircleDist = 0;
			rval1 = item.getX() - rightCircle.getBoundsInParent().getCenterX();
			rval2 = item.getY() - rightCircle.getBoundsInParent().getCenterY();
			rsqrtVal1 =  Math.pow(rval1, 2);
			rsqrtVal2 =  Math.pow(rval2, 2);
			rightCircleDist = Math.sqrt(rsqrtVal1 + rsqrtVal2);
			
			if(leftCircleDist <= leftCircle.getRadius() && rightCircleDist <= rightCircle.getRadius()) {
				groupIdentifier.setText("Intersect");
				groupIdentifier.setStyle("-fx-text-fill: blue;");
			}
			else if(leftCircleDist < leftCircle.getRadius()) {
				groupIdentifier.setText("Left Circle");
				groupIdentifier.setStyle("-fx-text-fill: red;");
			}
			else if(rightCircleDist < rightCircle.getRadius()) {
				groupIdentifier.setText("Right Circle");
				groupIdentifier.setStyle("-fx-text-fill: red;");
			}
			else {
				groupIdentifier.setText("Not in any set.");
				groupIdentifier.setStyle("-fx-text-fill: black;");
			}
		}
		else {
			leftCircleDist = 0;
			lval1 = item.getX() - leftCircle.getBoundsInParent().getCenterX();
			lval2 = item.getY() - leftCircle.getBoundsInParent().getCenterY();
			lsqrtVal1 =  Math.pow(lval1, 2);
			lsqrtVal2 =  Math.pow(lval2, 2);
			leftCircleDist = Math.sqrt(lsqrtVal1 + lsqrtVal2);
			
			rightCircleDist = 0;
			rval1 = item.getX() - rightCircle.getBoundsInParent().getCenterX();
			rval2 = item.getY() - rightCircle.getBoundsInParent().getCenterY();
			rsqrtVal1 =  Math.pow(rval1, 2);
			rsqrtVal2 =  Math.pow(rval2, 2);
			rightCircleDist = Math.sqrt(rsqrtVal1 + rsqrtVal2);
			
			bottomCircleDist = 0;
			bval1 = item.getX() - bottomCircle.getBoundsInParent().getCenterX();
			bval2 = item.getY() - bottomCircle.getBoundsInParent().getCenterY();
			bsqrtVal1 =  Math.pow(bval1, 2);
			bsqrtVal2 =  Math.pow(bval2, 2);
			bottomCircleDist = Math.sqrt(bsqrtVal1 + bsqrtVal2);
			
			if(leftCircleDist <= leftCircle.getRadius() && rightCircleDist <= rightCircle.getRadius() && bottomCircleDist < bottomCircle.getRadius()) {
				groupIdentifier.setText("Full Intersect");
				groupIdentifier.setStyle("-fx-text-fill: blue;");
				
			}
			else if(leftCircleDist <= leftCircle.getRadius() && rightCircleDist <= rightCircle.getRadius()) {
				groupIdentifier.setText("Left Right Intersect");
				groupIdentifier.setStyle("-fx-text-fill: green;");
			}
			else if(rightCircleDist <= rightCircle.getRadius() && bottomCircleDist < bottomCircle.getRadius()) {
				groupIdentifier.setText("Bottom Right Intersect");
				groupIdentifier.setStyle("-fx-text-fill: green;");
			}
			else if(leftCircleDist <= leftCircle.getRadius() && bottomCircleDist < bottomCircle.getRadius()) {
				groupIdentifier.setText("Bottom Left Intersect");
				groupIdentifier.setStyle("-fx-text-fill: green;");
			}
			else if(bottomCircleDist < bottomCircle.getRadius()) {
				groupIdentifier.setText("Bottom Circle");
				groupIdentifier.setStyle("-fx-text-fill: red;");
			}
			else if(leftCircleDist < leftCircle.getRadius()) {
				groupIdentifier.setText("Left Circle");
				groupIdentifier.setStyle("-fx-text-fill: red;");
			}
			else if(rightCircleDist < rightCircle.getRadius()) {
				groupIdentifier.setText("Right Circle");
				groupIdentifier.setStyle("-fx-text-fill: red;");
			}
			else {
				groupIdentifier.setText("Not in any set.");
				groupIdentifier.setStyle("-fx-text-fill: black;");
			}
		}
		
		
	}

}