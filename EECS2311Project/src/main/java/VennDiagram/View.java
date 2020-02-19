package VennDiagram;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

//merged




public class View extends Application{
	public static Stage primaryStage;
	public static Scene promptWindow;
	
	
	//private FXMLLoader loader;


	public static void main(String[] args) {
		launch(args);
	}

	
	

	@Override
	public void start(Stage stage) throws Exception{
//		System.out.printf("root = %s\n", getClass().getResource("/views/View.fxml"));
		
		
		primaryStage = stage;
		Parent root = FXMLLoader.load(getClass().getResource("/views/View.fxml"));
		   
	    Scene scene = new Scene(root, 800, 600);
		
		Text text = new Text("Welcome to Group 3's Venn Diagram Application!");
		text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		text.setFill(Color.RED);
		
	
		
		Button button = new Button("Take me to the next scene");
		
		button.setOnAction(e -> primaryStage.setScene(scene));
		
		StackPane layout1 = new StackPane();
		
		layout1.getChildren().addAll(text,button);
		StackPane.setAlignment(text, Pos.TOP_CENTER);
		
		promptWindow = new Scene(layout1,700,500);
		
	
	    primaryStage.setTitle("VennDiagram Creator");
	    primaryStage.setScene(promptWindow);
	    primaryStage.setResizable(true);
	    primaryStage.show();
	}
	

}
