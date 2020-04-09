package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.event.*;

public class advancedSettingsController {
	@FXML
	private CheckBox itemSorter;

	@FXML
	private CheckBox darkTheme;

	@FXML
	private CheckBox duplicateItems;

	@FXML
	private Button apply;
	
	public void initialize() {
		if(controller.startPageController.sortItems) {
			itemSorter.setSelected(true);
		}
		if(controller.startPageController.darkEnabled) {
			darkTheme.setSelected(true);
		}
		if(controller.startPageController.wantDuplicates) {
			duplicateItems.setSelected(true);
			
		}
	}
	
	@FXML
	protected void applyAndClose(ActionEvent event) {
//		System.out.println("want sorted: " + controller.startPageController.sortItems);
//		System.out.println("want Darkmode: " + controller.startPageController.darkEnabled);
//		System.out.println("Want repeats: "+ controller.startPageController.wantDuplicates);
		VennDiagram.advancedSettings.window.close();
	}
	
	@FXML
	protected void sortItemsCheck(ActionEvent event) {
		if(itemSorter.isSelected()) {
			controller.startPageController.sortItems = true;
		}
		else {
			controller.startPageController.sortItems = false;
		}
	}
	@FXML
	protected void darkModeEnabled(ActionEvent event) {
		if(darkTheme.isSelected()) {
			controller.startPageController.darkEnabled = true;
		}
		else {
			controller.startPageController.darkEnabled = false;
		}
	}
	
	@FXML
	protected void duplicatesAllowed(ActionEvent event) {
		if(duplicateItems.isSelected()) {
			controller.startPageController.wantDuplicates = true;
			VennDiagram.repeatDraggableItem.checkboxPressed = true;
		}
		else {
			controller.startPageController.wantDuplicates = false;
			VennDiagram.repeatDraggableItem.checkboxPressed = false;
		}
	}
}
