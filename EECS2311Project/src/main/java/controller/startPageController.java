package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.event.ActionEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook; 

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
    private Button loadNew;
    
    @FXML
    private Button loadPrev;
    
    public static boolean load = false;
    
    public static File selectedFile;
    
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
			View.primaryStage.setTitle("Venn Diagram");
			View.primaryStage.setScene(View.scene);	
		}
	
	/*
	 * Method activates when the folder is clicked on the startpage. The user will be prompted
	 * to select a file to load into the program. 
	 */
	@FXML
	public void loadFile(ActionEvent event) throws IOException {
		
			FileChooser fileChooser = new FileChooser();
			ExtensionFilter filter = new ExtensionFilter("Text Files","*.txt");
			filter.getExtensions();
			selectedFile = fileChooser.showOpenDialog(null);
			fileChooser.setTitle("Select a Text File");
			fileChooser.setSelectedExtensionFilter(filter);
			
			if(selectedFile == null) {
				//Do nothing
			}
			else {
				
			String fileExtension = selectedFile.getName().substring(selectedFile.getName().lastIndexOf("."),selectedFile.getName().length());
				
			if(!fileExtension.equals(".txt") && !fileExtension.equals(".xlx") && !fileExtension.equals(".xlsx")) {
				VennDiagram.invalidFileFormatAlert.display("Invalid File", "Select a .txt .xlx or .xlsx file.");
			}
			
			else if(fileExtension.equals(".xlx") || fileExtension.equals(".xlsx")) {
				parseXLSX();
				createTitleAndEnter();
				event.consume();
			}
			else {
				
				BufferedReader br = new BufferedReader(new FileReader(selectedFile));
				startPageController.load = true;
				String st;
				System.out.println(selectedFile.getName());
				while((st = br.readLine()) != null){
					Controller.loadData(st);
				}
				
				br.close();
				createTitleAndEnter();
				event.consume();
				}
			}
			
		}
	
	public void parseXLSX() throws IOException {
		FileInputStream fis = new FileInputStream(selectedFile);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow row = sheet.getRow(0);
		Cell c;
		Row r;
	
		for(int i = 0; i < row.getLastCellNum(); i++) {
			r = sheet.getRow(i);
			for(int j = 0; j < row.getLastCellNum(); j++) {
				c = r.getCell(j);
				
				if(c == null) {
					//Dont fill the listview
				}
				else {
					Controller.loadData(c.getStringCellValue());
				}
			}
		}
		
		
		wb.close();
		
	}
}