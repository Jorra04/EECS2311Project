package controller;

import VennDiagram.clearAllAlert;
import VennDiagram.restoreDefaultsAlert;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
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
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;
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
	public static int NUMIDS = 0;
	ContextMenu contextMenu = new ContextMenu();
	MenuItem RCdelete = new MenuItem("Delete");
	MenuItem RCrefactor = new MenuItem("Refactor");
	@FXML
	Slider circleSize;
	@FXML
	Slider leftCircleSlider;
	@FXML
	Slider rightCircleSlider;
	@FXML
	Slider bottomCircleSlider = new Slider();
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

	/*
	 * the following is for the possibility of three circles.
	 */

	@FXML
	public Label leftSetName;

	@FXML
	public Label rightSetName;
	Group bottomGroup;
	Group leftRightGroup;
	Group bottomLeftGroup;
	Group bottomRightGroup;
	Group fullIntersect;

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
    private MenuItem openTextFile;

    @FXML
    private MenuItem textFileSave;

    @FXML
    private MenuItem switchScene;

    @FXML
    private MenuItem openFile;

    @FXML
    private MenuItem saveFile;

    @FXML
    private MenuItem quitProgram;

    @FXML
    private MenuItem restoreDef;
    @FXML
    private MenuItem refactor;
    @FXML
    private MenuItem delete;
 
    @FXML
    private MenuItem undo;

    @FXML
    private MenuItem redo;
    
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
	ImageView save = new ImageView(new Image("/images/save.png"));
	ImageView load = new ImageView(new Image("/images/load.png"));
	ImageView importFile = new ImageView(new Image("/images/import.png"));
	ImageView export = new ImageView(new Image("/images/export.png"));
	ImageView quit = new ImageView(new Image("/images/exit.png"));
	ImageView backToMenu = new ImageView(new Image("/images/back.png"));
	
	ImageView refactorIM = new ImageView(new Image("/images/refactor.png"));
	ImageView deleteIM = new ImageView(new Image("/images/delete.png"));

	ImageView undoIM = new ImageView(new Image("/images/undo.png"));
	ImageView redoIM = new ImageView(new Image("/images/redo.png"));
	ImageView aboutIM = new ImageView(new Image("/images/aboutUs.png"));
	ImageView restoreIM = new ImageView(new Image("/images/restore.png"));
	
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
	public static DraggableItem clickedDraggable; 
	double minX;
	double maxX;
	double minY;
	double maxY;

	// new stuff i added 04-04
	boolean threeCircs = false;
	Circle bottomCircle = new Circle();
	
	//Queues for the tasklist
	public static Deque<Item> undoItem = new ArrayDeque<>();
	public static Deque<Item> redoItem = new ArrayDeque<>();
	
	//Queues for undo and redo
	public static Deque<DraggableItem> undoDItem = new ArrayDeque<>();
	public static Deque<DraggableItem> redoDItem = new ArrayDeque<>();
	
	//List for dragItem
	List <DraggableItem> currentItems = new ArrayList<DraggableItem>();
	
	//Last Action
	public static ArrayList<Integer> lastAction = new ArrayList<Integer>(); // 0 is add to list, 1 is add to window, 2 is move position
	public static ArrayList<Integer> lastRemovedAction = new ArrayList<Integer>();// 0 is add to list, 1 is add to window, 2 is move position

	public static ArrayList<Double> posx = new ArrayList<Double>();
	public static ArrayList<Double> posy = new ArrayList<Double>();
	
	public static File selectedFile;
	// use this to help setup the fxml components, initialize is called as soon as
	// app starts up. Similar to a constructor.
	 
	double lastValidX = 0;
	double lastValidY = 0;
	
	/*
	 * for the bottom circle.
	 */
	ContextMenu circleMenu = new ContextMenu();
	MenuItem Circledelete = new MenuItem("Delete");
	
	
	public void initialize() {

//		diagram_pane.setStyle(backgroundCol);
		
		circleMenu.getItems().add(Circledelete);
		
		save.setFitHeight(20);
		save.setFitWidth(20);
		textFileSave.setGraphic(save);
		
		load.setFitHeight(20);
		load.setFitWidth(20);
		openTextFile.setGraphic(load);
		
		importFile.setFitHeight(20);
		importFile.setFitWidth(20);
		openFile.setGraphic(importFile);

		export.setFitHeight(20);
		export.setFitWidth(20);
		saveFile.setGraphic(export);
		
		quit.setFitHeight(20);
		quit.setFitWidth(20);
		quitProgram.setGraphic(quit);
		
		backToMenu.setFitHeight(20);
		backToMenu.setFitWidth(20);
		switchScene.setGraphic(backToMenu);
		
		refactorIM.setFitHeight(20);
		refactorIM.setFitWidth(20);
		refactor.setGraphic(refactorIM);
	
		
		deleteIM.setFitHeight(20);
		deleteIM.setFitWidth(20);
		delete.setGraphic(deleteIM);
		

		
		undoIM.setFitHeight(20);
		undoIM.setFitWidth(20);
		undo.setGraphic(undoIM);
		
		redoIM.setFitHeight(20);
		redoIM.setFitWidth(20);
		redo.setGraphic(redoIM);
		
		aboutIM.setFitHeight(20);
		aboutIM.setFitWidth(20);
		aboutUs.setGraphic(aboutIM);
		
		restoreIM.setFitHeight(20);
		restoreIM.setFitWidth(20);
		restoreDef.setGraphic(restoreIM);
		
//		diagram_pane.setStyle(backgroundCol);
		minX = diagram_pane.getBoundsInParent().getMinX();
		maxX = diagram_pane.getBoundsInParent().getMaxX();
		minY = diagram_pane.getBoundsInParent().getMinY();
		maxY = diagram_pane.getBoundsInParent().getMaxY();

//		createDraggableItemButton.setStyle("-fx-background-color: #a8a496");
		create_text.requestFocus();
//		diagram_pane.setStyle("-fx-background-color: #F5F5DC");
//		item_list.setStyle("-fx-background-color: #F5F5DC");
		bottomCircle.setRadius(leftCircle.getRadius());
		bottomCircle.setCenterX(760);
		bottomCircle.setCenterY(700);
		bottomCircle.setOpacity(leftCircle.getOpacity());
		bottomCircle.setFill(leftCircle.getFill());
		contextMenu.getItems().addAll(RCrefactor, RCdelete);
		// create listeners on the diagram_pane's dimensions so its components can
		// resize as well
		item_list.setMinWidth(248);
		leftSetName.setLayoutY(rightSetName.getLayoutY());
		diagram_pane.widthProperty().addListener((obs, oldVal, newVal) -> {
			paneX = newVal.doubleValue();
			leftCircle.setLayoutX(paneX / 3);
			// move right circle to the right for intersection
			rightCircle.setLayoutX(paneX / 3 + paneX / 4);
			
			bottomCircle.setCenterX((leftCircle.getBoundsInParent().getCenterX() + rightCircle.getBoundsInParent().getCenterX())/2);
			
			rightSetName.setLayoutX(rightCircle.getBoundsInParent().getCenterX());
			leftSetName.setLayoutX(leftCircle.getBoundsInParent().getCenterX());
		});
		diagram_pane.heightProperty().addListener((obs, oldVal, newVal) -> {
			paneY = newVal.doubleValue();
			leftCircle.setLayoutY(paneY / 2);
			rightCircle.setLayoutY(paneY / 2);
			bottomCircle.setCenterY(leftCircle.getBoundsInParent().getCenterY() + leftCircle.getRadius());
			
		});
		
		VennDiagram.View.primaryStage.heightProperty().addListener((ChangeListener<? super Number>) new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				leftCircle.setRadius((double)newValue/3.33);
				rightCircle.setRadius((double)newValue/3.33);
				bottomCircle.setRadius((double)newValue/3.33);
//				System.out.println(leftCircle.getBoundsInLocal().intersects(item_list.getBoundsInLocal()));
			
			

			}
		});
//		VennDiagram.View.primaryStage.widthProperty().addListener((ChangeListener<? super Number>) new ChangeListener<Number>() {
//			@Override
//			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//				
//				leftCircle.setRadius((double)newValue/4);
//				rightCircle.setRadius((double)newValue/4);
//				System.out.println(leftCircle.getRadius() +" -> "+newValue);
//				System.out.println(rightCircle.getRadius()+" -> "+newValue);
//
//			}
//		});
		

		leftSetName.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@SuppressWarnings("unchecked")
			@Override
			public void handle(MouseEvent event) {
				if(event.getClickCount() == 2 && event.getButton().equals(MouseButton.PRIMARY)) {
					try {
						controller.changeSetNameController.text = leftSetName.getText();
						controller.changeSetNameController.color = (Color) leftSetName.getTextFill();
						VennDiagram.changeSetNameWindow.display("Change Title");
						if (controller.changeSetNameController.buttonPressed) {
							leftSetName.setText(controller.changeSetNameController.text);
							leftSetName.setText(controller.changeSetNameController.text);
							// need to loop through elements and change the element to whatever we changed
							// it to in the refactor.
							leftSetName.setTextFill(controller.changeSetNameController.color);
						}

					} catch (Exception e1) {
						e1.printStackTrace();

					}
				}
			}
		});
		rightSetName.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@SuppressWarnings("unchecked")
			@Override
			public void handle(MouseEvent event) {
				if(event.getClickCount() == 2 && event.getButton().equals(MouseButton.PRIMARY)) {
					try {
						controller.changeSetNameController.text = rightSetName.getText();
						controller.changeSetNameController.color = (Color) rightSetName.getTextFill();
						VennDiagram.changeSetNameWindow.display("Change Title");
						if (controller.changeSetNameController.buttonPressed) {
							rightSetName.setText(controller.changeSetNameController.text);
							rightSetName.setText(controller.changeSetNameController.text);
							// need to loop through elements and change the element to whatever we changed
							// it to in the refactor.
							rightSetName.setTextFill(controller.changeSetNameController.color);
						}

					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
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
		model.createGroup("Left Right Intersection");
		model.createGroup("Bottom Right Intersection");
		model.createGroup("Bottom Left Intersection");
		model.createGroup("Bottom");
		model.createGroup("Full Intersection");
		leftGroup = model.getGroupMap().get("left");
		rightGroup = model.getGroupMap().get("right");
		matchGroup = model.getGroupMap().get("match");

		bottomGroup = model.getGroupMap().get("Bottom");
		leftRightGroup = model.getGroupMap().get("Left Right Intersection");
		bottomLeftGroup = model.getGroupMap().get("Bottom Left Intersection");
		bottomRightGroup = model.getGroupMap().get("Bottom Right Intersection");
		fullIntersect = model.getGroupMap().get("Full Intersection");

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


		/*
		 * resizing circles.
		 */
		circleSize.setRotate(180);
		circleSize.setValue(366);
		bottomCircleSlider.setMax(bottomCircle.getCenterY());
		bottomCircleSlider.setMin(600);
		circleSize.valueProperty().addListener((ChangeListener<? super Number>) new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				leftCircle.setRadius((double) newValue);
				rightCircle.setRadius((double) newValue);
				bottomCircle.setRadius((double) newValue);
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
		bottomCircleSlider.valueProperty().addListener((ChangeListener<? super Number>) new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//				System.out.println(oldValue);
				bottomCircle.setCenterY((double) newValue);

			}
		});
		
		bottomCircle.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@SuppressWarnings("unchecked")
			@Override
			public void handle(MouseEvent event) {
				if(event.getButton().equals(MouseButton.SECONDARY)) {
					try {
						circleMenu.show(View.primaryStage, event.getScreenX(), event.getScreenY());
						circleMenu.getItems().get(0).setOnAction(e -> {
							circleDestroyer(circles);
						});

					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
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
		leftSetName.setText(controller.startPageController.leftSetName);
		rightSetName.setText(controller.startPageController.rightSetName);
	}
	
	/*
	 * sorts the ListView. Call this when you want to resort the list.
	 */
	
	protected void itemListSorter() {
		Collections.sort(item_list.getItems(), new Comparator<Item>() {
			@Override
			public int compare(Item item1, Item item2) {
				for (int i = 0; i < item1.getText().length() &&  
	                    i < item2.getText().length(); i++) { 
	            if ((int)item1.getText().toLowerCase().charAt(i) ==  
	                (int)item2.getText().toLowerCase().charAt(i)) { 
	                continue; 
	            }  
	            else { 
	                return (int)item1.getText().charAt(i) -  
	                    (int)item2.getText().charAt(i); 
	            } 
	        } 
	  
	  
	        if (item1.getText().length() < item2.getText().length()) { 
	            return (item1.getText().length()-item2.getText().length()); 
	        }  
	        else if (item1.getText().length() > item2.getText().length()) { 
	            return (item1.getText().length()-item2.getText().length()); 
	        } 
	        else { 
	            return 0; 
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
		if (toolbar.getItems().contains(Controller.box)) {
			toolbar.getItems().remove(Controller.box);
			Color colorVal = (Color) leftCircle.getFill();
			colorPicker.setValue(colorVal);
		}
		splitMenu.setText("Left Circle");
		toolbar.getItems().add(Controller.box);


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
		if (!threeCircs) {
			TagAlreadyExistsAlert.display("**WARNING**",
					"You do not have a third circle enabled. Add an extra circle to get the most\n"
							+ "out of this functionality.");
			return;
		}
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

		Group groupArray[] = new Group[8];
		groupArray[0] = leftGroup;
		groupArray[1] = rightGroup;
		groupArray[2] = matchGroup;
		groupArray[3] = bottomGroup;
		groupArray[4] = leftRightGroup;
		groupArray[5] = bottomLeftGroup;
		groupArray[6] = bottomRightGroup;
		groupArray[7] = fullIntersect;
		Item clickedItem1  = item_list.getSelectionModel().getSelectedItem();
		
		//Warn the user once or more times if they don't select the option.
		if (VennDiagram.repeatDraggableItem.checkboxPressed2) {

		} else {
			VennDiagram.repeatDraggableItem.display2("Alert", "Are you sure you want to delete this item?");
		}
		if(!VennDiagram.repeatDraggableItem.confirmPressed2) {
			return;
		}
		for (int i = 0; i < 7; i++) {
			if (groupArray[i].contains(clickedItem1)) {
				groupArray[i].removeItem(clickedItem1);	
			}
			model.getItemSet().remove(clickedItem1);
			item_list.getItems().remove(clickedItem1); //remove it from the list out here so even if 
			//it's not in a group, it will still be removed.
		}
		for(Node node : diagram_pane.getChildren()) {
			if(node.getClass().equals(DraggableItem.class) && ((DraggableItem)node).text.getText().equals(clickedItem1.getText())) {
				diagram_pane.getChildren().remove( node );
				break;
			}
		}
		
		item_list.refresh();
//		leftSetText.setText(leftGroup.toVisualList());
//		rightSetText.setText(rightGroup.toVisualList());
//		middleSetText.setText(matchGroup.toVisualList());
		groupIdentifier.clear();
		event.consume();
	}

	/*
	 * New Method: testMouse() detects if an item is clicked and what that item
	 * object is isItemClicked: returns true if an item is clicked clickedItem:
	 * returns the Item object associated with the click
	 */
//	@FXML
	public boolean testMouse(MouseEvent event) {
		isItemClicked = item_list.isFocused();
		System.out.println("Item is focused: " + item_list.isFocused());
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
	
	protected void overlappingItems(DraggableItem item) {
		for(Node node : diagram_pane.getChildren()) {
			if(node.getClass().equals(DraggableItem.class)) {
				if(((DraggableItem)node).getX() == item.getX() && ((DraggableItem)node).getY() == item.getY() ) {
					item.setLayoutY(item.getLayoutY() + 30);
				}
			}
		}
	}
	
	
	
	protected boolean isOverlapping(DraggableItem item) {
		for(Node node : diagram_pane.getChildren()) {
			
			if(node.getClass().equals(DraggableItem.class) && !((DraggableItem)node).equals(item)) {
				if(((DraggableItem)node).getBoundsInParent().intersects(item.getBoundsInParent())) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	protected void overlapCorrector(DraggableItem tempItem) {
		while(isOverlapping(tempItem)) {
			tempItem.setLayoutY(tempItem.getLayoutY()+30);
			isOverlapping(tempItem);

		}
		lastValidX = tempItem.getLayoutX();
		lastValidY = tempItem.getLayoutY();
	}
	
	
	

	@FXML
	public void handleCreateDraggableItemButton(ActionEvent event) {
		// if the selected items in the item list is 0, don't do anything
		if (selectedItems.size() <= 0) {
			event.consume();
			return;
		}
		double itemPositionY = 0;
		double itemPositionX = 0;
		for (Item item : selectedItems) {
			lastAction.add(1);
			// provide each item with a reference to the controller class itself, so it can
			// compare the item with the circle positions for intersection.
			DraggableItem tempItem = new DraggableItem(item, this);
			tempItem.getLabel().setTextFill(Color.BLACK); // tbh it looks gray but w.e.
			tempItem.getLabel().setFont(new Font("Arial", 18));
			tempItem.setPrefSize(Region.USE_COMPUTED_SIZE + 50, Region.USE_COMPUTED_SIZE + 25);
			//changed the above line to 25 from 50. Did this because this allows the items to be placed closer.
//			tempItem.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
//			use the above line to visualize what the label object actually looks like.
			// set position within diagram pane
			itemPositionY += spaceY;
			if (itemPositionY >= paneY - 100) {
				itemPositionX += spaceX; // if current "column" goes past the diagram dimensions, go to the next
											// "column"
				itemPositionY = 0 + spaceY;
			}
			tempItem.setLayoutX(itemPositionX); // set to the left
			tempItem.setLayoutY(itemPositionY); // space between each item
			undoDItem.addFirst(tempItem);
			currentItems.add(tempItem);
			System.out.println("orig position (" + tempItem.getX() + ",)" + tempItem.getY());

			if (!containsArray.contains(tempItem.getItem().getText())
					|| VennDiagram.repeatDraggableItem.checkboxPressed) {
				diagram_pane.getChildren().add(tempItem);
				if(item_list.getSelectionModel().getSelectedItems().size() == 1) {
					overlappingItems(tempItem);
				}
				containsArray.add(tempItem.getItem().getText());
				lastValidX = tempItem.getLayoutX();
				lastValidY = tempItem.getLayoutY();
				

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
										if(controller.startPageController.sortItems) {
											itemListSorter();
										}
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
									System.out.println("deleted");

									FadeTransition ft = new FadeTransition(Duration.millis(1000), tempItem);

									ft.setFromValue(1.0);
									ft.setToValue(0);
									ft.setAutoReverse(true);

									ft.play();
									ft.setOnFinished(e1 -> {
										diagram_pane.getChildren().remove(tempItem);
									});

									groupIdentifier.clear(); // clears the group identifier.
									item_list.refresh();
								} catch (Exception e1) {

								}
							});

						}
					} catch (Exception e) {

					}

				}
			});
	
			tempItem.setOnMouseReleased(e -> {
				
				if(!( tempItem.getLayoutX() > minX && tempItem.getLayoutX() < diagram_pane.getBoundsInLocal().getMaxX() 
						) || !( tempItem.getLayoutY() > diagram_pane.getBoundsInLocal().getMinY() && 
								tempItem.getLayoutY() < diagram_pane.getBoundsInLocal().getMaxY() 
								)) {
					tempItem.setLayoutX(lastValidX);
					tempItem.setLayoutY(lastValidY);
//					System.out.println(tempItem.getLayoutX() + " > " + diagram_pane.getBoundsInLocal().getMinX());
//					System.out.println(tempItem.getLayoutX() > minX);
//					System.out.println(tempItem.getLayoutX() +" < "+ diagram_pane.getBoundsInLocal().getMaxX());
//					System.out.println(tempItem.getLayoutX() < diagram_pane.getBoundsInLocal().getMaxX() );
//					System.out.println(tempItem.getLayoutY() +" > "+ diagram_pane.getBoundsInLocal().getMinY());
//					System.out.println(tempItem.getLayoutY() > diagram_pane.getBoundsInLocal().getMinY());
//					System.out.println(tempItem.getLayoutY() +" < "+ diagram_pane.getBoundsInLocal().getMaxY());
//					System.out.println(tempItem.getLayoutY() < diagram_pane.getBoundsInLocal().getMaxY());
					
//					System.out.println(diagram_pane.getMinHeight() + "+ "+ diagram_pane.getMinWidth());
				}
				overlapCorrector(tempItem);
				groupFinder2(tempItem);
				if (!threeCircs) {
					if (groupIdentifier.getText().equals("Left Circle")) {
						leftGroup.insertItem(tempItem.item);
						if (rightGroup.items.containsValue(tempItem.item)) {
							rightGroup.removeItem(tempItem.item);
						}
						if (matchGroup.items.containsValue(tempItem.item)) {
							matchGroup.removeItem(tempItem.item);
						}
					} else if (groupIdentifier.getText().equals("Right Circle")) {
						rightGroup.insertItem(tempItem.item);

						if (leftGroup.items.containsValue(tempItem.item)) {
							leftGroup.removeItem(tempItem.item);
						}
						if (matchGroup.items.containsValue(tempItem.item)) {
							matchGroup.removeItem(tempItem.item);
						}
					} else if (groupIdentifier.getText().equals("Intersect")) {
						matchGroup.insertItem(tempItem.item);
						if (rightGroup.items.containsValue(tempItem.item)) {
							rightGroup.removeItem(tempItem.item);

						}
						if (leftGroup.items.containsValue(tempItem.item)) {
							leftGroup.removeItem(tempItem.item);
						}
					} else {
						if (rightGroup.items.containsValue(tempItem.item)) {
							rightGroup.removeItem(tempItem.item);

						}
						if (leftGroup.items.containsValue(tempItem.item)) {
							leftGroup.removeItem(tempItem.item);
						}
						if (matchGroup.items.containsValue(tempItem.item)) {
							matchGroup.removeItem(tempItem.item);
						}
					}
					
				} else {
					if (groupIdentifier.getText().equals("Left Circle")) {
						leftGroup.insertItem(tempItem.item);
						if (rightGroup.items.containsValue(tempItem.item)) {
							rightGroup.removeItem(tempItem.item);
						}
						if (bottomGroup.items.containsValue(tempItem.item)) {
							bottomGroup.removeItem(tempItem.item);
						}
						if (fullIntersect.items.containsValue(tempItem.item)) {
							fullIntersect.removeItem(tempItem.item);
						}
						if (leftRightGroup.items.containsValue(tempItem.item)) {
							leftRightGroup.removeItem(tempItem.item);
						}

						if (bottomLeftGroup.items.containsValue(tempItem.item)) {
							bottomLeftGroup.removeItem(tempItem.item);
						}
						if (bottomRightGroup.items.containsValue(tempItem.item)) {
							bottomRightGroup.removeItem(tempItem.item);
						}
					} else if (groupIdentifier.getText().equals("Right Circle")) {
						rightGroup.insertItem(tempItem.item);
						if (leftGroup.items.containsValue(tempItem.item)) {
							leftGroup.removeItem(tempItem.item);
						}
						if (bottomGroup.items.containsValue(tempItem.item)) {
							bottomGroup.removeItem(tempItem.item);
						}
						if (fullIntersect.items.containsValue(tempItem.item)) {
							fullIntersect.removeItem(tempItem.item);
						}
						if (leftRightGroup.items.containsValue(tempItem.item)) {
							leftRightGroup.removeItem(tempItem.item);
						}

						if (bottomLeftGroup.items.containsValue(tempItem.item)) {
							bottomLeftGroup.removeItem(tempItem.item);
						}
						if (bottomRightGroup.items.containsValue(tempItem.item)) {
							bottomRightGroup.removeItem(tempItem.item);
						}
					} else if (groupIdentifier.getText().equals("Bottom Circle")) {
						bottomGroup.insertItem(tempItem.item);
						if (leftGroup.items.containsValue(tempItem.item)) {
							leftGroup.removeItem(tempItem.item);
						}
						if (rightGroup.items.containsValue(tempItem.item)) {
							rightGroup.removeItem(tempItem.item);
						}
						if (fullIntersect.items.containsValue(tempItem.item)) {
							fullIntersect.removeItem(tempItem.item);
						}
						if (leftRightGroup.items.containsValue(tempItem.item)) {
							leftRightGroup.removeItem(tempItem.item);
						}

						if (bottomLeftGroup.items.containsValue(tempItem.item)) {
							bottomLeftGroup.removeItem(tempItem.item);
						}
						if (bottomRightGroup.items.containsValue(tempItem.item)) {
							bottomRightGroup.removeItem(tempItem.item);
						}
					}

					else if (groupIdentifier.getText().equals("Left Right Intersect")) {
						leftRightGroup.insertItem(tempItem.item);
						if (leftGroup.items.containsValue(tempItem.item)) {
							leftGroup.removeItem(tempItem.item);
						}
						if (rightGroup.items.containsValue(tempItem.item)) {
							rightGroup.removeItem(tempItem.item);
						}
						if (fullIntersect.items.containsValue(tempItem.item)) {
							fullIntersect.removeItem(tempItem.item);
						}
						if (bottomGroup.items.containsValue(tempItem.item)) {
							bottomGroup.removeItem(tempItem.item);
						}

						if (bottomLeftGroup.items.containsValue(tempItem.item)) {
							bottomLeftGroup.removeItem(tempItem.item);
						}
						if (bottomRightGroup.items.containsValue(tempItem.item)) {
							bottomRightGroup.removeItem(tempItem.item);
						}
					} else if (groupIdentifier.getText().equals("Bottom Right Intersect")) {
						bottomRightGroup.insertItem(tempItem.item);
						if (leftGroup.items.containsValue(tempItem.item)) {
							leftGroup.removeItem(tempItem.item);
						}
						if (rightGroup.items.containsValue(tempItem.item)) {
							rightGroup.removeItem(tempItem.item);
						}
						if (fullIntersect.items.containsValue(tempItem.item)) {
							fullIntersect.removeItem(tempItem.item);
						}
						if (bottomGroup.items.containsValue(tempItem.item)) {
							bottomGroup.removeItem(tempItem.item);
						}

						if (bottomLeftGroup.items.containsValue(tempItem.item)) {
							bottomLeftGroup.removeItem(tempItem.item);
						}
						if (leftRightGroup.items.containsValue(tempItem.item)) {
							leftRightGroup.removeItem(tempItem.item);
						}
					} else if (groupIdentifier.getText().equals("Bottom Left Intersect")) {
						bottomLeftGroup.insertItem(tempItem.item);
						if (leftGroup.items.containsValue(tempItem.item)) {
							leftGroup.removeItem(tempItem.item);
						}
						if (rightGroup.items.containsValue(tempItem.item)) {
							rightGroup.removeItem(tempItem.item);
						}
						if (fullIntersect.items.containsValue(tempItem.item)) {
							fullIntersect.removeItem(tempItem.item);
						}
						if (bottomGroup.items.containsValue(tempItem.item)) {
							bottomGroup.removeItem(tempItem.item);
						}

						if (bottomRightGroup.items.containsValue(tempItem.item)) {
							bottomRightGroup.removeItem(tempItem.item);
						}
						if (leftRightGroup.items.containsValue(tempItem.item)) {
							leftRightGroup.removeItem(tempItem.item);
						}
					}

					else if (groupIdentifier.getText().equals("Full Intersect")) {
						fullIntersect.insertItem(tempItem.item);
						if (leftGroup.items.containsValue(tempItem.item)) {
							leftGroup.removeItem(tempItem.item);
						}
						if (rightGroup.items.containsValue(tempItem.item)) {
							rightGroup.removeItem(tempItem.item);
						}
						if (bottomLeftGroup.items.containsValue(tempItem.item)) {
							bottomLeftGroup.removeItem(tempItem.item);
						}
						if (bottomGroup.items.containsValue(tempItem.item)) {
							bottomGroup.removeItem(tempItem.item);
						}

						if (bottomRightGroup.items.containsValue(tempItem.item)) {
							bottomRightGroup.removeItem(tempItem.item);
						}
						if (leftRightGroup.items.containsValue(tempItem.item)) {
							leftRightGroup.removeItem(tempItem.item);
						}
					} else {
						if (leftGroup.items.containsValue(tempItem.item)) {
							leftGroup.removeItem(tempItem.item);
						}
						if (rightGroup.items.containsValue(tempItem.item)) {
							rightGroup.removeItem(tempItem.item);
						}
						if (bottomLeftGroup.items.containsValue(tempItem.item)) {
							bottomLeftGroup.removeItem(tempItem.item);
						}
						if (bottomGroup.items.containsValue(tempItem.item)) {
							bottomGroup.removeItem(tempItem.item);
						}

						if (bottomRightGroup.items.containsValue(tempItem.item)) {
							bottomRightGroup.removeItem(tempItem.item);
						}
						if (leftRightGroup.items.containsValue(tempItem.item)) {
							leftRightGroup.removeItem(tempItem.item);
						}
						if (fullIntersect.items.containsValue(tempItem.item)) {
							fullIntersect.removeItem(tempItem.item);
						}
					}


				}

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
			toolbar.getItems().add(bottomCircleSlider);
			numCirc++; // inc the number of circles.
		} else {
			// >4 cicles is not allowed, this will check any circle creation beyond 4.
			tooManyCirclesAlert.display("Exceeded Number of Allowed Circles!",
					"You have exceeded the " + "number of allowed cirlces.");
		}
	}

	private void circleCreator(double radius, int startX, int startY) {
		// making the circles
		if (threeCircs) {
			return;
		}
		threeCircs = true;
		bottomCircle.setOpacity(leftCircle.getOpacity());
		diagram_pane.getChildren().add(0, bottomCircle);

		circles.add(bottomCircle);
	}

	private void circleDestroyer(List<Circle> circle) {
		toolbar.getItems().remove(bottomCircleSlider);
		// delete the circles.
		diagram_pane.getChildren().removeAll(circle);
		Controller.numCirc = 2; // resets the value of numCirc, allows for the other functions to work.
		leftRightGroup.removeAll();
		bottomGroup.removeAll();
		bottomLeftGroup.removeAll();
		bottomRightGroup.removeAll();
		fullIntersect.removeAll();
		threeCircs = false;
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
			View.primaryStage.setResizable(false);
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
			if (threeCircs) {
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
			
			// the rest is resetting original vals.
			leftCircle.setFill(Color.DODGERBLUE);
			rightCircle.setFill(Color.DODGERBLUE);
			Color colorVal = (Color) rightCircle.getFill();
			colorPicker.setValue(colorVal);
			VennDiagram.repeatDraggableItem.checkboxPressed = false;
			event.consume();
		}

	}

	private void remover() { // function to remove all items, used in multiple places.
		// removing from the groups.
		rightGroup.removeAll();
		leftGroup.removeAll();
		matchGroup.removeAll();
		if(threeCircs) {
			leftRightGroup.removeAll();
			bottomGroup.removeAll();
			bottomLeftGroup.removeAll();
			bottomRightGroup.removeAll();
			fullIntersect.removeAll();
		}
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
		if (threeCircs) {
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
				undoItem.addFirst(testing);
				lastAction.add(0);
				itemsContent.add(testing);
				create_text.clear(); // reset textfield
				create_text.requestFocus(); // get the textfield to listen for the next input.
			}
			if(controller.startPageController.sortItems) {
				itemListSorter();
				item_list.refresh();
			}		
		}
	}

	@FXML
	public void undoEvent(ActionEvent event) {
		if(lastAction.get(lastAction.size()-1) ==0 ) {
			itemsContent.remove(undoItem.getFirst()); //Remove element from the items list
			redoItem.addFirst(undoItem.getFirst());//Add the most recent popped item to a new queue so we can redo if needed
			undoItem.pop(); // Pop the item off the stack
			item_list.refresh();// Refresh the item list
			lastRemovedAction.add(0);
			lastAction.remove(lastAction.size()-1);
		}
		
		else if(lastAction.get(lastAction.size()-1) ==1) {
			DraggableItem tempItem = undoDItem.getFirst();
			redoDItem.addFirst(undoDItem.getFirst());
			undoDItem.pop();
			lastAction.remove(lastAction.size()-1);
			lastRemovedAction.add(1);
			model.getItemSet().remove(tempItem.getItem());
			int index = containsArray.indexOf(tempItem.getItem().getText());
			containsArray.remove(index);
			diagram_pane.getChildren().remove(tempItem);
		}
		
		else if(lastAction.get(lastAction.size()-1) ==2) {
			for(DraggableItem search : currentItems) {
				if(search.text.getText().equals(undoItem.getFirst().getText())) {
					double nX = search.getLayoutX();
					double nY = search.getLayoutY();
					search.setLayoutX(posx.get(posx.size()-1));
					search.setLayoutY(posy.get(posy.size()-1));
					posx.set(posx.size()-1, nX);
					posy.set(posy.size()-1, nY);
					redoItem.addFirst(undoItem.getFirst());
					undoItem.pop();
					lastRemovedAction.add(2);
					lastAction.remove(lastAction.size()-1);
					}
				}
			}
		
		else {
			
		}
		event.consume();
	}
	
	@FXML
	public void redoEvent(ActionEvent event) {
		
		if(lastRemovedAction.get(lastRemovedAction.size()-1) ==0 ) {
			
		itemsContent.add(redoItem.getFirst());
		undoItem.addFirst(redoItem.getFirst());
		lastAction.add(0);
		redoItem.pop();
		lastRemovedAction.remove(lastRemovedAction.size()-1);
		item_list.refresh();	
		}
		
		else if(lastRemovedAction.get(lastRemovedAction.size()-1) ==1) {
			DraggableItem tempItem = redoDItem.getFirst();
			undoDItem.addFirst(tempItem);
			containsArray.add(tempItem.getItem().getText());
			redoDItem.pop();
			lastRemovedAction.remove(lastRemovedAction.size() - 1);
			lastAction.add(1);
			model.getItemSet().add(tempItem.getItem());
			diagram_pane.getChildren().add(tempItem);		
		}
		
		else if (lastRemovedAction.get(lastRemovedAction.size()-1) ==2) {
			for(DraggableItem search : currentItems) {
				if(search.text.getText().equals(undoItem.getFirst().getText())) {
					double nX = search.getLayoutX();
					double nY = search.getLayoutY();
					search.setLayoutX(posx.get(posx.size()-1));
					search.setLayoutY(posy.get(posy.size()-1));
					posx.set(posx.size()-1, nX);
					posy.set(posy.size()-1, nY);
					undoItem.addFirst(redoItem.getFirst());
					redoItem.pop();
					lastAction.add(2);
					lastRemovedAction.remove(lastRemovedAction.size()-1);
					}
				}
			
		}
		event.consume();
	}
	

	
	protected void createFromTextFile(String text) {
		Item testing = new Item(text.trim());
		if (!model.containsText(testing.text)) {
			model.addSet(testing);
			itemsContent.add(testing);
			create_text.clear(); // reset textfield
			create_text.requestFocus(); // get the textfield to listen for the next input.
		}
		if(controller.startPageController.sortItems) {
			itemListSorter();
			item_list.refresh();
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

//	@FXML
//	protected void refactor(ActionEvent event) throws Exception {
//		if (isItemClicked == false) { // no selected items.
//			VennDiagram.TagAlreadyExistsAlert.display("ERROR", "Select some items before trying to rename them.");
//		}
//
//		else {
//			VennDiagram.refactorWindow.display("Window");
//			if (!controller.refactorController.buttonPressed) { // checks if the exit button is pressed
//				// or if the refactor button is pressed.
//				return;
//			}
//			if (model.containsText(refactorController.text)) { // stops duplicates.
//				VennDiagram.TagAlreadyExistsAlert.display("ERROR", "Tag Already Exists");
//				return;
//			} else {
//				clickedItem.text = refactorController.text;
//			}
//			item_list.refresh(); // refresh the listView to show us the current values.
//		}
//	}
	
	@FXML
	protected void refactor(ActionEvent event) throws Exception{
		if(item_list.getSelectionModel().getSelectedItems().size() == 0) {
			VennDiagram.TagAlreadyExistsAlert.display("ERROR", "Select some items before trying to rename them.");
		}
		else {
			controller.refactorController.text = item_list.getSelectionModel().getSelectedItem().getText();

			controller.refactorController.description = "No description Given.";
			VennDiagram.refactorWindow.display("Refactor");
			if (!controller.refactorController.buttonPressed) { // checks if the exit button is pressed
				// or if the refactor button is pressed.
				return;
			}
			
			for(Node node : diagram_pane.getChildren()) {
				if(node.getClass().equals(DraggableItem.class)) {
					if(((DraggableItem)node).text.getText().equals(item_list.getSelectionModel().getSelectedItem().getText())) {
						((DraggableItem)node).text.setText(refactorController.text);
					}
				}
			}
			
			item_list.getSelectionModel().getSelectedItem().setText(refactorController.text);
			if(controller.startPageController.sortItems) {
				itemListSorter();
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
		double rval2;
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
		if (!threeCircs) {
			leftCircleDist = 0;
			lval1 = item.getX() - leftCircle.getBoundsInParent().getCenterX();
			lval2 = item.getY() - leftCircle.getBoundsInParent().getCenterY();
			lsqrtVal1 = Math.pow(lval1, 2);
			lsqrtVal2 = Math.pow(lval2, 2);
			leftCircleDist = Math.sqrt(lsqrtVal1 + lsqrtVal2);

			rightCircleDist = 0;
			rval1 = item.getX() - rightCircle.getBoundsInParent().getCenterX();
			rval2 = item.getY() - rightCircle.getBoundsInParent().getCenterY();
			rsqrtVal1 = Math.pow(rval1, 2);
			rsqrtVal2 = Math.pow(rval2, 2);
			rightCircleDist = Math.sqrt(rsqrtVal1 + rsqrtVal2);

			if (leftCircleDist <= leftCircle.getRadius() && rightCircleDist <= rightCircle.getRadius()) {
				groupIdentifier.setText("Intersect");
				groupIdentifier.setStyle("-fx-text-fill: green;");
			} else if (leftCircleDist < leftCircle.getRadius()) {
				groupIdentifier.setText("Left Circle");
				groupIdentifier.setStyle("-fx-text-fill: red;");
			} else if (rightCircleDist < rightCircle.getRadius()) {
				groupIdentifier.setText("Right Circle");
				groupIdentifier.setStyle("-fx-text-fill: red;");
			} else {
				groupIdentifier.setText("Not in any set.");
				groupIdentifier.setStyle("-fx-text-fill: black;");
			}
		} else {
			leftCircleDist = 0;
			lval1 = item.getX() - leftCircle.getBoundsInParent().getCenterX();
			lval2 = item.getY() - leftCircle.getBoundsInParent().getCenterY();
			lsqrtVal1 = Math.pow(lval1, 2);
			lsqrtVal2 = Math.pow(lval2, 2);
			leftCircleDist = Math.sqrt(lsqrtVal1 + lsqrtVal2);

			rightCircleDist = 0;
			rval1 = item.getX() - rightCircle.getBoundsInParent().getCenterX();
			rval2 = item.getY() - rightCircle.getBoundsInParent().getCenterY();
			rsqrtVal1 = Math.pow(rval1, 2);
			rsqrtVal2 = Math.pow(rval2, 2);
			rightCircleDist = Math.sqrt(rsqrtVal1 + rsqrtVal2);

			bottomCircleDist = 0;
			bval1 = item.getX() - bottomCircle.getBoundsInParent().getCenterX();
			bval2 = item.getY() - bottomCircle.getBoundsInParent().getCenterY();
			bsqrtVal1 = Math.pow(bval1, 2);
			bsqrtVal2 = Math.pow(bval2, 2);
			bottomCircleDist = Math.sqrt(bsqrtVal1 + bsqrtVal2);

			if (leftCircleDist <= leftCircle.getRadius() && rightCircleDist <= rightCircle.getRadius()
					&& bottomCircleDist < bottomCircle.getRadius()) {
				groupIdentifier.setText("Full Intersect");
				groupIdentifier.setStyle("-fx-text-fill: blue;");

			} else if (leftCircleDist <= leftCircle.getRadius() && rightCircleDist <= rightCircle.getRadius()) {
				groupIdentifier.setText("Left Right Intersect");
				groupIdentifier.setStyle("-fx-text-fill: green;");
			} else if (rightCircleDist <= rightCircle.getRadius() && bottomCircleDist < bottomCircle.getRadius()) {
				groupIdentifier.setText("Bottom Right Intersect");
				groupIdentifier.setStyle("-fx-text-fill: green;");
			} else if (leftCircleDist <= leftCircle.getRadius() && bottomCircleDist < bottomCircle.getRadius()) {
				groupIdentifier.setText("Bottom Left Intersect");
				groupIdentifier.setStyle("-fx-text-fill: green;");
			} else if (bottomCircleDist < bottomCircle.getRadius()) {
				groupIdentifier.setText("Bottom Circle");
				groupIdentifier.setStyle("-fx-text-fill: red;");
			} else if (leftCircleDist < leftCircle.getRadius()) {
				groupIdentifier.setText("Left Circle");
				groupIdentifier.setStyle("-fx-text-fill: red;");
			} else if (rightCircleDist < rightCircle.getRadius()) {
				groupIdentifier.setText("Right Circle");
				groupIdentifier.setStyle("-fx-text-fill: red;");
			} else {
				groupIdentifier.setText("Not in any set.");
				groupIdentifier.setStyle("-fx-text-fill: black;");
			}
		}

	}

	@FXML
	protected void textFileWriter(ActionEvent event) {
		try {
			FileChooser fileChooser = new FileChooser();
			selectedFile = fileChooser.showSaveDialog(null);
			
			FileWriter writer = new FileWriter(selectedFile, false);
			if (!threeCircs) {
				writer.write("Left Group:");
				int i = 0;
				for (Item item : leftGroup.items.values()) {
					if (i == leftGroup.getSize() - 1) {
						writer.write(item.getText());
					} else {
						writer.write(item.getText() + ", ");
					}
					i++;

				}
				i = 0;
				writer.write("\n");
				writer.write("Match Group:");
				for (Item item : matchGroup.items.values()) {
					if (i == matchGroup.getSize() - 1) {
						writer.write(item.getText());
					} else {
						writer.write(item.getText() + ", ");
					}
					i++;

				}
				writer.write("\n");
				i = 0;
				writer.write("Right Group:");
				for (Item item : rightGroup.items.values()) {
					if (i == rightGroup.getSize() - 1) {
						writer.write(item.getText());
					} else {
						writer.write(item.getText() + ", ");
					}
					i++;

				}
				writer.write("\n");

				
			}
			else {
			
				int i = 0;
				writer.write("Left Group:");
				for (Item item : leftGroup.items.values()) {
					if (i == leftGroup.getSize() - 1) {
						writer.write(item.getText());
					} else {
						writer.write(item.getText() + ", ");
					}
					i++;

				}
				writer.write("\n");
			
				i = 0;
				writer.write("Right Group:");
				for (Item item : rightGroup.items.values()) {
					if (i == rightGroup.getSize() - 1) {
						writer.write(item.getText());
					} else {
						writer.write(item.getText() + ", ");
					}
					i++;

				}
				writer.write("\n");
				i = 0;
				writer.write("Bottom Group:");
				for (Item item : bottomGroup.items.values()) {
					if (i == bottomGroup.getSize() - 1) {
						writer.write(item.getText());
					} else {
						writer.write(item.getText() + ", ");
					}
					i++;

				}
				writer.write("\n");
	
				i = 0;
				writer.write("Left Right Group:");
				for (Item item : leftRightGroup.items.values()) {
					if (i == leftRightGroup.getSize() - 1) {
						writer.write(item.getText());
					} else {
						writer.write(item.getText() + ", ");
					}
					i++;

				}
				writer.write("\n");

				i = 0;
				writer.write("Bottom Left Group:");
				for (Item item : bottomLeftGroup.items.values()) {
					if (i == bottomLeftGroup.getSize() - 1) {
						writer.write(item.getText());
					} else {
						writer.write(item.getText() + ", ");
					}
					i++;

				}
				writer.write("\n");
	
				i = 0;
				writer.write("Bottom Right Group:");
				for (Item item : bottomRightGroup.items.values()) {
					if (i == bottomRightGroup.getSize() - 1) {
						writer.write(item.getText());
					} else {
						writer.write(item.getText() + ", ");
					}
					i++;

				}
				writer.write("\n");
			
				i = 0;
				writer.write("Match Group:");
				for (Item item : fullIntersect.items.values()) {
					if (i == fullIntersect.getSize() - 1) {
						writer.write(item.getText());
					} else {
						writer.write(item.getText() + ", ");
					}
					i++;

				}
				writer.write("\n");
			}
			
			writer.close();
		} catch (Exception e) {

		}
	}
	@FXML
	protected void openFromTextfile(ActionEvent action) throws Exception {
		FileChooser fileChooser = new FileChooser();
		selectedFile = fileChooser.showOpenDialog(null);
		fileChooser.setTitle("Select a Text File");
		if(selectedFile == null) {
			return;
		}
		else {
			
		String fileExtension = selectedFile.getName().substring(selectedFile.getName().lastIndexOf("."),selectedFile.getName().length());
			
		if(!fileExtension.equals(".txt")) {
			VennDiagram.invalidFileFormatAlert.display("Invalid File", "Select a .txt file.");
			return;
		}
		String[] words = null;
		String s;
		FileReader fr = new FileReader(selectedFile);
		BufferedReader br = new BufferedReader(fr);
		
		while((s=br.readLine())!= null) {
			words = s.split(" ");
			for(String word: words) {
				createFromTextFile(word);
			}
		}
		}
	}

}