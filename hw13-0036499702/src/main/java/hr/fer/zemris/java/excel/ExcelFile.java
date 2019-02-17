package hr.fer.zemris.java.excel;

import  java.io.*;  
import  org.apache.poi.hssf.usermodel.HSSFSheet;  
import  org.apache.poi.hssf.usermodel.HSSFWorkbook;

import  org.apache.poi.hssf.usermodel.HSSFRow;  

/**
 * Represents xls file. Has methods for generating values to put into Excel workbook and 
 * method for writing that workbook to given output stream.
 * It takes 3 arguments:
 * 		a = lowest value
 * 		b = highest value
 * 		n = number of powers to calculate.
 * Excel workbook that is generated has n sheets, each of those sheets with index i has 
 * a table of int values between a and b risen to i-th power.
 * @author Alex
 *
 */
public class ExcelFile {
	
	/**
	 * Lowest value.
	 */
	private int a;
	
	/**
	 * Hightest value.
	 */
	private int b;
	
	/**
	 * Number of powers.
	 */
	private int n;
	
	/**
	 * Excel workbook.
	 */
	private HSSFWorkbook hwb;
	
	/**
	 * Constructor of ExcelFile.
	 * @param a
	 * 			lowest value
	 * @param b
	 * 			highest value
	 * @param n
	 * 			power
	 */
	public ExcelFile(int a, int b, int n) {
		
		this.a = a;
		this.b = b;
		this.n = n;
		
		generateFile();
		
	}
	
	/**
	 * Generates sheets and values that are put into workbook.
	 */
	private void generateFile() {
		hwb = new HSSFWorkbook();
		
		for(int i = 1; i <= n; i++) {
			HSSFSheet sheet =  hwb.createSheet(Integer.toString(i));
			
			HSSFRow rowhead = sheet.createRow((short)0);
			rowhead.createCell((short) 0).setCellValue("Number");
			rowhead.createCell((short) 1).setCellValue(i + "-th power of number");
			
			
			for(int j = a; j <= b; j++) {
				HSSFRow row = sheet.createRow((short) (j - a + 1));
				row.createCell((short) 0).setCellValue(j);
				row.createCell((short) 1).setCellValue(Math.pow(j, i));
			}
			
		}	
	}
	
	/**
	 * Writes workbook to given output stream.
	 * @param out 
	 * 				output stream
	 * @throws IOException
	 */
	public void write(OutputStream out) throws IOException {
		hwb.write(out);
		hwb.close();
	}

}
