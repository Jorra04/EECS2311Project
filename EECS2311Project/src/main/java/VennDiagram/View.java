package VennDiagram;



import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

//merged

public class View extends Application{
	public static Stage primaryStage;
	public static Scene promptWindow;
	public static Scene refactor;
	public static Scene scene;


	public static void main(String[] args) {
		launch(args);
	}



	@Override
	public void start(Stage stage) throws Exception{
//		System.out.printf("root = %s\n", getClass().getResource("/views/View.fxml"));
		
		
		primaryStage = stage;
		Parent root = FXMLLoader.load(getClass().getResource("/views/View.fxml"));
		Parent root2 = FXMLLoader.load(getClass().getResource("/views/openingPage.fxml"));
		   
	    //scene = new Scene(root, 900, 650);
	    scene = new Scene(root);
		promptWindow = new Scene(root2,750,550);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setOnCloseRequest(event ->{
			quitProgramAlert.display("Confirm Exit", "Are you sure you want to exit?");
			if(!quitProgramAlert.closePressed) {
				event.consume();
			}
			
		});
		
	    primaryStage.setTitle("VennDiagram Creator");
	    primaryStage.setScene(promptWindow);
	    primaryStage.setResizable(true);
	    //primaryStage.getIcons().add(0,new Image(getClass().getResourceAsStream("/images/icon.png")));
	    primaryStage.show();
	}
	

}