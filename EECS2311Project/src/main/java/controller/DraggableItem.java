package controller;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import model.Item;
import model.VennModel;

/**
 * 
 * @author brian
 * 
 * This class is to create a draggable item, to be inserted onto a pane. This class uses an Item object from model.
 *
 */

public class DraggableItem extends Pane{
	
	private double x = 0;
	private double y = 0;
	
	//mouse coordinates
	private double mx = 0;
	private double my = 0;
	
	private boolean isDragging = false;
	
	public Item item;
	private Label text;
	
	private Controller mainController;
	private VennModel model;
	
	//constructor requires a reference to the main controller so it can compare to the circles
	public DraggableItem(Item item, Controller mainController) {
		this.item = item;
		this.text = new Label(item.getText());
		this.mainController = mainController;
		//create a reference to the main controller's model
		this.model = mainController.model;
		this.getChildren().add(text);
		
		//----------------------------------------------------------------------------------------------------------------
		//make item draggable
		
		//record location at mouse pressed
		EventHandler<MouseEvent> eventHandlerMousePressed = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				//if items are stacked on each other on z plane, bring most recently clicked item to front
				toFront();
				
				mx = event.getSceneX();
				my = event.getSceneY();
				
				x = getLayoutX();
				y = getLayoutY();
				
				event.consume();
			};
		};
		this.setOnMousePressed(eventHandlerMousePressed);
		
		//act of dragging the label
		EventHandler<MouseEvent> eventHandlerMouseDragged = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				double deltaX = event.getSceneX() - mx;
				double deltaY = event.getSceneY() - my;
				
				x += deltaX;
				y += deltaY;
				
				double tempX = x;
				double tempY = y;
				
				setLayoutX(tempX);
				setLayoutY(tempY);
				
				isDragging = true;
				
				mx = event.getSceneX();
				my = event.getSceneY();
				
				event.consume();
			};
		};
		this.setOnMouseDragged(eventHandlerMouseDragged);
		
		//on mouse release, see where it was dropped, if it was dropped in circles then start model logic
		EventHandler<MouseEvent> eventHandlerMouseReleased = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(isIntersecting(mainController.leftCircle) && isIntersecting(mainController.rightCircle)) {
					System.out.println("is intersecting with both circles");
				} else if(isIntersecting(mainController.rightCircle)) {
					System.out.println("is intersecting with right circle");
				} else if(isIntersecting(mainController.leftCircle)) {
					System.out.println("is intersecting with left circle");
				}
			};
		};
		this.setOnMouseReleased(eventHandlerMouseReleased);
		//----------end of drag functionality-----------------------------------------------------
	}
	
	public void setItem(Item item) {
		//set new item
		this.item = item;
		//set the label's text
		this.text.setText(item.getText());
	}
	
	public Item getItem() {
		return this.item;
	}
	
	public Label getLabel() {
		return this.text;
	}
	
	//checks if the draggable item itnersects with a given shape, usually the circle.
	//returns true if intersecting is detected
	public boolean isIntersecting(Shape shape) {
		if(this.getBoundsInParent().intersects(shape.getBoundsInParent())) {
			return true;
		} else {
			return false;
		}
	}
	
}
