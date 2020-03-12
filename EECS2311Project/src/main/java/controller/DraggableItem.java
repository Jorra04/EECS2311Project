package controller;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.Item;

public class DraggableItem extends Pane{
	
	private double x = 0;
	private double y = 0;
	
	//mouse coordinates
	private double mx = 0;
	private double my = 0;
	
	private boolean isDragging = false;
	
	public Item item;
	private Label text;
	
	private DraggableItem(Item item) {
		this.item = item;
		this.text.setText(item.getText());
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
	}
	
	public void setItem(Item item) {
		this.item = item;
	}
	
	public Item getItem() {
		return this.item;
	}
	
}
