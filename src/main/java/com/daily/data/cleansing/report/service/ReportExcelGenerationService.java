package com.daily.data.cleansing.report.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.daily.data.cleansing.report.dao.ReportDao;
import com.daily.data.cleansing.report.email.SimpleEmail;
import com.daily.data.cleansing.report.model.ReportBean;

@Service
public class ReportExcelGenerationService {

	@Autowired
	private ReportDao reportDao;
	
	@Autowired
	private SimpleEmail simpleEmail;
	
	private Logger LOG=Logger.getLogger(this.getClass());

	private final String folderPath = System.getProperty("user.home")+"/Documents/DataExport";

	public List<ReportBean> findDetails() {
		List<ReportBean> reportList = reportDao.findAll();
		writeExcelFile(generateFile(folderPath), reportList);
		return reportList;
		
	}
	public void sendEmail() {
		simpleEmail.sendMail(generateFile(folderPath));
	}

	private File generateFile(String folderPath) {

		SimpleDateFormat sdformat = new SimpleDateFormat("MMM-dd-yyyy");
		Date date = new Date();
		String fileName = "Report_" + sdformat.format(date) + ".xlsx";
		File dir = new File(folderPath);
		File file = new File(folderPath + "/" + fileName);
		// Create only if it does not exist already
		try {
			if (!dir.exists()) {
				dir.mkdir();
			}
			if (!file.exists()) {
				file.createNewFile();
			}

		} catch (IOException ie) {
			ie.printStackTrace();
		}
		return file;

	}

	private void writeExcelFile(File file, List<ReportBean> reportList) {

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Error Correction");
		int rowCount = 0;

		createHeaderRow(workbook, sheet, rowCount);
		createBody(sheet, rowCount, reportList);

		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(file);
			workbook.write(outputStream);
			outputStream.flush();
			System.gc();  //XSSFWORKBOOK can cause memory issue
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	private void createBody(XSSFSheet sheet, int rowCount, List<ReportBean> reportList) {
		SimpleDateFormat sf=new SimpleDateFormat("dd/MMM/YYYY");
		for (ReportBean report : reportList) {
			XSSFRow xssRow = sheet.createRow(++rowCount);
			Cell bodyCell = xssRow.createCell(0);
			bodyCell.setCellValue(report.getEmail());
			bodyCell = xssRow.createCell(1);
			bodyCell.setCellValue(String.valueOf(report.getId()));//converting bigint to string seCellValue can accept double but not good way
			bodyCell = xssRow.createCell(2);
			bodyCell.setCellValue(report.getAsset());
			bodyCell = xssRow.createCell(3);
			bodyCell.setCellValue(report.getRampEventType());
			bodyCell = xssRow.createCell(4);
			bodyCell.setCellValue(report.getLoadStatus());
			bodyCell = xssRow.createCell(5);
			bodyCell.setCellValue(report.getStatus());
			bodyCell = xssRow.createCell(6);
			bodyCell.setCellValue(sf.format(report.getCreateDate()));
			bodyCell = xssRow.createCell(7);
			bodyCell.setCellValue(sf.format(report.getUpdateDate()));
			bodyCell = xssRow.createCell(8);
			bodyCell.setCellValue(String.valueOf(report.getOriginalID()));

		}
	}

	private void createHeaderRow(XSSFWorkbook workbook, XSSFSheet sheet, int row) {
		CellStyle cs = workbook.createCellStyle();
		cs.setWrapText(true);

		XSSFRow xssRow = sheet.createRow(row);

		Cell headerCell = xssRow.createCell(0);
		headerCell.setCellValue("EMAIL");
		headerCell = xssRow.createCell(1);
		headerCell.setCellValue("ID");
		headerCell = xssRow.createCell(2);
		headerCell.setCellValue("ASSET");
		headerCell = xssRow.createCell(3);
		headerCell.setCellValue("RAMP_EVENT_TYPE");
		headerCell = xssRow.createCell(4);
		headerCell.setCellValue("LOAD_STATUS");
		headerCell = xssRow.createCell(5);
		headerCell.setCellValue("STATUS");
		headerCell = xssRow.createCell(6);
		headerCell.setCellValue("CREATE_DATE");
		headerCell = xssRow.createCell(7);
		headerCell.setCellValue("UPDATE_DATE");
		headerCell = xssRow.createCell(8);
		headerCell.setCellValue("ORIGINAL_ID");

		sheet.setColumnWidth(0, poiWidth(35.0));
		sheet.setColumnWidth(1, poiWidth(24.0));
		sheet.setColumnWidth(2, poiWidth(24.0));
		sheet.setColumnWidth(3, poiWidth(24.0));
		sheet.setColumnWidth(4, poiWidth(24.0));
		sheet.setColumnWidth(5, poiWidth(24.0));
		sheet.setColumnWidth(6, poiWidth(24.0));
		sheet.setColumnWidth(7, poiWidth(24.0));
		sheet.setColumnWidth(8, poiWidth(24.0));
		
		row++;
	}
	private int poiWidth(double width) {
        return (int) Math.round(width * 256 + 200);
    }
}
