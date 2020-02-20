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
	public static Scene scene;
	
	//private FXMLLoader loader;


	public static void main(String[] args) {
		launch(args);
	}



	@Override
	public void start(Stage stage) throws Exception{
//		System.out.printf("root = %s\n", getClass().getResource("/views/View.fxml"));
		
		
		primaryStage = stage;
		Parent root = FXMLLoader.load(getClass().getResource("/views/View.fxml"));
		Parent root2 = FXMLLoader.load(getClass().getResource("/views/openingPage.fxml"));
		   
	    scene = new Scene(root, 800, 600);
	    
		promptWindow = new Scene(root2,750,550);
		
		primaryStage.setOnCloseRequest(event ->{
			quitProgram.display("Exit", "Are you sure you want to exit?");
			if(!quitProgram.closePressed) {
				event.consume();
			}
			
		});
		
	
	    primaryStage.setTitle("VennDiagram Creator");
	    primaryStage.setScene(promptWindow);
	    primaryStage.setResizable(true);
	    primaryStage.show();
	}
	

}
