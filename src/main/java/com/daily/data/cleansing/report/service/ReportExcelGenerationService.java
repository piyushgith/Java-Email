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
import com.daily.data.cleansing.report.model.ReportBean;

@Service
public class ReportExcelGenerationService {

	@Autowired
	private ReportDao reportDao;
	
	private Logger LOG=Logger.getLogger(this.getClass());

	private final String folderPath = System.getProperty("user.home")+"/Documents/DataExport";

	public List<ReportBean> findDetails() {
		List<ReportBean> reportList = reportDao.findAll();
		writeExcelFile(generateFile(folderPath), reportList);
		return reportList;
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
		SimpleDateFormat sf=new SimpleDateFormat("MM/dd/YYYY");
		for (ReportBean report : reportList) {
			XSSFRow xssRow = sheet.createRow(++rowCount);
			Cell bodyCell = xssRow.createCell(0);
			bodyCell.setCellValue(report.getId());
			bodyCell = xssRow.createCell(1);
			bodyCell.setCellValue(report.getCalled_by());
			bodyCell = xssRow.createCell(2);
			bodyCell.setCellValue(report.getCalled_to());
			bodyCell = xssRow.createCell(3);
			bodyCell.setCellValue(sf.format(report.getCalled_on()));
			bodyCell = xssRow.createCell(4);
			bodyCell.setCellValue(report.getDuration());

		}
	}

	private void createHeaderRow(XSSFWorkbook workbook, XSSFSheet sheet, int row) {
		CellStyle cs = workbook.createCellStyle();
		cs.setWrapText(true);

		XSSFRow xssRow = sheet.createRow(row);
		//xssRow.setRowStyle(cs);
		// cs.setAlignment(HorizontalAlignment.LEFT);

		Cell headerCell = xssRow.createCell(0);
		headerCell.setCellValue("ID");
		headerCell = xssRow.createCell(1);
		headerCell.setCellValue("CALLED_BY");
		headerCell = xssRow.createCell(2);
		headerCell.setCellValue("CALLED_TO");
		headerCell = xssRow.createCell(3);
		headerCell.setCellValue("CALLED_DATE");
		headerCell = xssRow.createCell(4);
		headerCell.setCellValue("DURATION");

		sheet.setColumnWidth(0, poiWidth(24.0));
		sheet.setColumnWidth(1, poiWidth(24.0));
		sheet.setColumnWidth(2, poiWidth(24.0));
		sheet.setColumnWidth(3, poiWidth(24.0));
		sheet.setColumnWidth(4, poiWidth(24.0));
		
		row++;
	}
	private int poiWidth(double width) {
        return (int) Math.round(width * 256 + 200);
    }
}
