package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;

import java.io.File;

import VennDiagram.View;

public class startPageController {

    @FXML
    private AnchorPane anchorpane;

    @FXML
    private Text title;

    @FXML
    private Button next;
    
    @FXML
    private ImageView imView;
	
    
    @FXML
    private MenuItem newFile;

    @FXML
    private MenuItem openFile;

    @FXML
    private MenuItem saveFile;

    @FXML
    private MenuItem quitProgram;
	

	
	
	
	@FXML
	protected void nextScene() {
		View.primaryStage.setScene(View.scene);
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
		try {

			File pdfFile = new File("src\\main\\resources\\userMan\\manual.pdf");
			if (pdfFile.exists()) {

				if (java.awt.Desktop.isDesktopSupported()) {
					java.awt.Desktop.getDesktop().open(pdfFile);
				} else {
					System.out.println("Awt Desktop is not supported!");
				}
			} else {
				System.out.println("File is not exists!");
			}
		  } catch (Exception ex) {
			ex.printStackTrace();
		  }
		
		event.consume();
	}
	
	
	
	
	
	
	
	
	
	
}
