package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
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
    private MenuItem aboutUs;
    
    @FXML
    private TextField diagramTitle;
    
    @FXML
    private Button loadNew;
    
    @FXML
    private Button loadPrev;
    
    public static boolean load = false;
    
    public static File selectedFile;
    
    public static boolean isFinishedLoading = false;
    
    /*
     * images sources from http://www.iconarchive.com/search?q=load+file
     */
    
    ImageView saveIM = new ImageView(new Image("/images/save.png"));
	ImageView loadIM = new ImageView(new Image("/images/load.png"));
	ImageView newIM = new ImageView(new Image("/images/newFile2.png"));
	ImageView quit = new ImageView(new Image("/images/exit.png"));
	ImageView aboutUsIM = new ImageView(new Image("/images/aboutUs.png"));
	ImageView newButtonIM = new ImageView(new Image("/images/newFileButton.png"));
	ImageView loadButtonIM = new ImageView(new Image("/images/loadFileButton.png"));
	
    public void initialize() {
 
    	saveIM.setFitHeight(20);
    	saveIM.setFitWidth(20);
    	saveFile.setGraphic(saveIM);
		
		loadIM.setFitHeight(20);
		loadIM.setFitWidth(20);
		openFile.setGraphic(loadIM);
		
		newIM.setFitHeight(20);
		newIM.setFitWidth(20);
		newFile.setGraphic(newIM);

		quit.setFitHeight(20);
		quit.setFitWidth(20);
		quitProgram.setGraphic(quit);
		
		aboutUsIM.setFitHeight(20);
		aboutUsIM.setFitWidth(20);
		aboutUs.setGraphic(aboutUsIM);
    	
		loadNew.setBackground(Background.EMPTY);
		newButtonIM.setFitHeight(200);
		newButtonIM.setFitWidth(200);
		loadNew.setGraphic(newButtonIM);
		loadPrev.setBackground(Background.EMPTY);
		loadButtonIM.setFitHeight(200);
		loadButtonIM.setFitWidth(200);
		loadPrev.setGraphic(loadButtonIM);
    	
    	
    	
    	FadeTransition ft = new FadeTransition(Duration.millis(1500),loadNew);
    	FadeTransition ft2 = new FadeTransition(Duration.millis(1500),loadPrev);
    	RotateTransition rt = new RotateTransition(Duration.millis(3000),loadNew);
    	RotateTransition rt2 = new RotateTransition(Duration.millis(3000),loadPrev);
    	loadNew.setOnMouseEntered(e->{
    		
        	ft.setFromValue(1.0);
        	ft.setToValue(0.1);
        	
        	ft.setCycleCount(Timeline.INDEFINITE);
        	ft.setAutoReverse(true);
        	ft.play();
        	
    	});
    	
    	loadPrev.setOnMouseEntered(e->{
    		
        	ft2.setFromValue(1.0);
        	ft2.setToValue(0.1);
        	
        	ft2.setCycleCount(Timeline.INDEFINITE);
        	ft2.setAutoReverse(true);
        	ft2.play();
        	
        
    	});
    	loadNew.setOnMouseExited(e->{
    		ft.jumpTo(Duration.ZERO);
    		ft.stop();
    		
    	});
    	loadPrev.setOnMouseExited(e->{
    		ft2.jumpTo(Duration.ZERO);
    		ft2.stop();
    		
    	});
//    	rt.setAxis(Rotate.Y_AXIS);
//		rt.setByAngle(360);
//		rt.setCycleCount(50);
//		rt.setAutoReverse(true);
//		rt.play();
//		rt2.setAxis(Rotate.Y_AXIS);
//		rt2.setByAngle(360);
//		rt2.setCycleCount(50);
//		rt2.setAutoReverse(true);
//		rt2.play();

    	
    	
    }
    
    
    
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
		VennDiagram.quitProgramAlert.window.setX(VennDiagram.View.promptWindow.getX() + 100);
		VennDiagram.quitProgramAlert.window.setY(VennDiagram.View.promptWindow.getY() + 50);
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
		View.primaryStage.setMaximized(true);
		View.primaryStage.hide();
		View.primaryStage.show();
	}
	
	/*
	 * Method activates when the folder is clicked on the startpage. The user will be prompted
	 * to select a file to load into the program. 
	 * FileChooser -> opens up a prompt for users to select a file
	 * selectedFile -> is the actual file
	 * load -> records the state of the program. True if a file has been loaded
	 */
	@FXML
	public void loadFile(ActionEvent event) throws IOException {
		
			FileChooser fileChooser = new FileChooser();
			selectedFile = fileChooser.showOpenDialog(null);
			fileChooser.setTitle("Select a Text File");
			load = true;
			
			if(selectedFile == null) {
				//Do nothing
			}
			else {
				
			String fileExtension = selectedFile.getName().substring(selectedFile.getName().lastIndexOf("."),selectedFile.getName().length());
				
			if(!fileExtension.equals(".txt") && !fileExtension.equals(".xls") && !fileExtension.equals(".xlsx")) {
				VennDiagram.invalidFileFormatAlert.display("Invalid File", "Select a .txt .xls or .xlsx file.");
			}
			
			else if(fileExtension.equals(".xlsx")) {
				parseXLSX();
				createTitleAndEnter();
				event.consume();
			}
			
			else if(fileExtension.equals(".xls")) {
				parseXLS();
				createTitleAndEnter();
				event.consume();
			}
			else {
				
				BufferedReader br = new BufferedReader(new FileReader(selectedFile));
				startPageController.load = true;
				String st;
				while((st = br.readLine()) != null){
					Controller.loadData(st);
				}
				isFinishedLoading = true;
				br.close();
				createTitleAndEnter();
				event.consume();
				}
			}
			
		}
	
	/*
	 * Reads an XLSX file and iterates over all rows an columns. Only accepts numeric and string characters.
	 */
	
	public void parseXLSX() throws IOException {
		FileInputStream fis = new FileInputStream(selectedFile);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheetAt(0);
		for(Row row : sheet) {
			for(Cell cell : row) {
				if(cell == null) {
					//Do nothing
				}
				
				else if (cell.getCellType() == CellType.NUMERIC) {
					DataFormatter df = new DataFormatter();
					String input = df.formatCellValue(cell);
					Controller.loadData(input);
				}
				
				else {
					Controller.loadData(cell.getStringCellValue());
				}
			}
		}
		wb.close();
	}
	
	/*
	 * Reads an XLSX file and iterates over all rows an columns. Only accepts numeric and string characters.
	 */
	
	public void parseXLS() throws IOException {
		FileInputStream fis = new FileInputStream(selectedFile);
		HSSFWorkbook wb = new HSSFWorkbook(fis);
		HSSFSheet sheet = wb.getSheetAt(0);
		for(Row row : sheet) {
			for(Cell cell : row) {
				if(cell == null) {
					//Do nothing
				}
				
				else if (cell.getCellType() == CellType.NUMERIC) {
					DataFormatter df = new DataFormatter();
					String input = df.formatCellValue(cell);
					Controller.loadData(input);
				}
				
				else {
					Controller.loadData(cell.getStringCellValue());
				}
			}
		}
		
		wb.close();
	}
}