package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;

import java.io.File;

import VennDiagram.DiagramTitleTooSmallAlert;
import VennDiagram.View;

public class startPageController {

    @FXML
    private AnchorPane anchorpane;

    @FXML
    private Text title;

    @FXML
    private Button next;
	
    @FXML
    private MenuItem newFile;

    @FXML
    private MenuItem openFile;

    @FXML
    private MenuItem saveFile;

    @FXML
    private MenuItem quitProgram;
    @FXML
    private TextField diagramTitle;
	

	
	@FXML
	public void nextScene(ActionEvent event) {
		createTitleAndEnter();
		event.consume();
	}
	@FXML
	protected void handleCreateTextFieldAction(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ENTER)) {
			createTitleAndEnter();
		}
		event.consume();
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
		int FileNotFoundCount = 0;
		try {

			File pdfFile = new File("EECS2311Project\\resources\\userMan\\manual.pdf");
			if (pdfFile.exists()) {

				if (java.awt.Desktop.isDesktopSupported()) {
					java.awt.Desktop.getDesktop().open(pdfFile);
				} else {
					System.out.println("Awt Desktop is not supported!");
				}
			}
			else {
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
		if(FileNotFoundCount > 1 ) {
			System.out.println(FileNotFoundCount);
			System.out.println("File Not Found");
		}
		
		event.consume();
	}
	
	private void createTitleAndEnter() {
		if( diagramTitle.getText().length() != 0 ) {
			View.primaryStage.setTitle(diagramTitle.getText());
			View.primaryStage.setScene(View.scene);	
		}
		else {
			VennDiagram.DiagramTitleTooSmallAlert.display("Default title","Do you want to create project with default title?");
			if(DiagramTitleTooSmallAlert.confirmPressed) {
				View.primaryStage.setScene(View.scene);	
			}
			
		}
	}
	
	
}
