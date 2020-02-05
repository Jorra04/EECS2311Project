package VennDiagram;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.application.Application;
import javafx.event.EventHandler;
//import javafx.event.EventHandler;
//import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
//import javafx.scene.layout.StackPane;
import javafx.scene.Group;
import javafx.scene.text.*;
import javafx.stage.Stage;


public class Main extends Application{
	
	//Button button;
	model.Item item;

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		primaryStage.setTitle("Venn Diagram");
	
		Circle circle1 = new Circle();
		circle1.setCenterX(250.0f);
		circle1.setCenterY(135.0f);
		circle1.setRadius(100.0f);
		circle1.setStroke(Color.GREEN);
		circle1.setFill(Color.rgb(200, 200, 200, 0.5));
		
		Circle circle2 = new Circle();
		circle2.setCenterX(350.0f);
		circle2.setCenterY(135.0f);
		circle2.setRadius(100.0f);
		circle2.setStroke(Color.BLUE);
		circle2.setFill(Color.rgb(255, 0, 0, 0.2));
//		
		
//		adds button to Venn Diagram Application 

        Button button = new Button ("Click me");
        
        TextField textField = new TextField ("Enter");
        textField.setPrefHeight(20);
        textField.setPrefWidth(6* textField.getPrefHeight());
        textField.setLayoutX(0);
        textField.setLayoutY(30);
        button.setLayoutY(textField.getLayoutY() + 1.25*textField.getPrefHeight());
        button.setLayoutX(textField.getLayoutX()); 
        button.setBackground(Background.EMPTY);
       
        //StackPane r = new StackPane(); 
		
        
        
		Group root = new Group();
		
		root.getChildren().add(circle1);
		root.getChildren().add(circle2);
	
		root.getChildren().add(textField);
		root.getChildren().add(button);
		
	

		
		Scene scene = new Scene(root, 600, 300);
		primaryStage.setScene(scene);
		primaryStage.show();
		//comment
		//barts comment
		//jorra's comment
		
	}
	
}

