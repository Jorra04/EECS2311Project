package VennDiagram;

import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.application.Application;
//import javafx.event.EventHandler;
//import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
//import javafx.scene.layout.StackPane;
import javafx.scene.Group;

import javafx.stage.Stage;


public class Main extends Application{
	
	//Button button;

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		//brian change
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
		Button button = new Button("New Button");
		
		Scene scene1 = new Scene(button, 200, 100);
		button.setBackground(Background.EMPTY);
        primaryStage.setScene(scene1);
        primaryStage.show();

//		//adds button to Venn Diagram Application 
		
        
        
		Group root = new Group();
		
		root.getChildren().add(circle1);
		root.getChildren().add(circle2);
		root.getChildren().add(button);
		
		Scene scene = new Scene(root, 600, 300);
		primaryStage.setScene(scene);
		primaryStage.show();
		//comment
		//barts comment
		//jorra's comment
	}
}

