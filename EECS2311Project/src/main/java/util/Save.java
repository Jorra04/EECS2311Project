package util;

import java.io.File;  
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.Group;
import model.Item;
import model.VennModel;  


public class Save {
	public static void writeExcel(VennModel model) throws IOException, InvalidFormatException {
		int headerSize = model.groupMapSize();
		ArrayList<String> groupTitles = new ArrayList<String>();
		ArrayList<Group> groups = new ArrayList<Group>();
		for (Group group : model.getGroupMap().values()) {
			groupTitles.add(group.getTitle());
			groups.add(group);
		}
		
		Workbook workbook = new XSSFWorkbook(); //generate xls file
		CreationHelper createHelper = workbook.getCreationHelper();
		
		Sheet sheet = workbook.createSheet("Venn Diagram State");
		
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 16);
		headerFont.setUnderline((byte) 1);
		
		//create a cell style with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);
		
		Row headerRow = sheet.createRow(0);
		
		//setting headers
		for(int i=0; i < headerSize + 1; i++) {
			Cell cell = headerRow.createCell(i);
			//first iteration, column header should be listview, subsequent headers are group title names
			if(i==0) {
				cell.setCellValue("All Items");
				cell.setCellStyle(headerCellStyle);
				//TODO: add the content for all items
				continue;
			}
			cell.setCellValue(groupTitles.get(i-1));
			cell.setCellStyle(headerCellStyle);
			int rowCount = 1;
			for(Item item : groups.get(i-1).getItems().values()) {
				System.out.println(item.toString());
				Row row = sheet.createRow(rowCount++);
				row.createCell(i).setCellValue(item.toString());
			}
		}
		
		for(int i = 0; i < headerSize + 1; i++) {
            sheet.autoSizeColumn(i);
        }
		
		FileOutputStream fileOut = new FileOutputStream("test.xlsx");
        workbook.write(fileOut);
        fileOut.close();
	}
}
