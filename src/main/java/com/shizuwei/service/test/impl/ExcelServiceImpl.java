package com.shizuwei.service.test.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFPictureData;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.shizuwei.service.test.ExcelService;

public class ExcelServiceImpl implements ExcelService {
	
	//List<> table = new LinkedList<String>();
	
	public static void main(String[] args) throws Exception {
		String excelFileName = "D:\\a.xlsx";
		File exelFile = new File(excelFileName);
		if (!exelFile.exists()) {
			return;
		}
		InputStream inputStream = new FileInputStream(exelFile);
		XSSFWorkbook wb = new XSSFWorkbook(inputStream); 
		 
		XSSFSheet sheet = wb.getSheetAt(1);
		
		System.out.println(sheet.getSheetName());
		
		int firtRowNum = sheet.getFirstRowNum();
		
		//int lastRowNum = sheet.getLastRowNum();
		
		
		XSSFRow row0 = sheet.getRow(firtRowNum);
		
		XSSFCell cell00 = row0.getCell(0);
		
		String cellValue = cell00.getStringCellValue();
		
		System.out.println(cellValue);
		 
	    XSSFDrawing hssfPatriarch = sheet.getDrawingPatriarch();
		
	    List<XSSFShape> shapes = hssfPatriarch.getShapes();
	    
	    for(XSSFShape shape:shapes){
	    	if(shape instanceof XSSFPicture){
	    		XSSFPicture picture = (XSSFPicture)shape;
	    		System.out.println("--"+picture.getShapeName());
	    		XSSFPictureData pictureData = picture.getPictureData();
	    		System.out.println(pictureData.suggestFileExtension());
	    		if(picture.getAnchor() instanceof XSSFClientAnchor){
	    			XSSFClientAnchor anchor = (XSSFClientAnchor)picture.getAnchor();
	    			System.out.println(anchor.toString());
	    			
	    		}
	    	}
	    }
	    
	    wb.close();
	    
	}

}
