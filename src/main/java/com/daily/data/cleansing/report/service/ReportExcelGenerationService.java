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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

	private Logger LOG = Logger.getLogger(this.getClass());

	private final String folderPath = System.getProperty("user.home") + "/Desktop/DataExport";

	public String findDetails() {
		List<Map<String, Object>> rows = reportDao.findAll();
		writeExcelFile(generateFile(folderPath), rows);
		return "Generated the report";

	}

	public void sendEmail() {
		simpleEmail.sendMail(generateFile(folderPath)); // not working due to proxy
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

	private void writeExcelFile(File file, List<Map<String, Object>> rowsList) {

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Error Correction");
		int rowCount = 0;
		List<String> columnList = new LinkedList<>();// Adding column names to create excel
		rowsList.get(0).forEach((K, V) -> {
			columnList.add(K);
		});
		// System.out.println(columnList.size());

		createHeaderRow(workbook, sheet, rowCount, columnList);
		createBody(sheet, rowCount, rowsList, columnList);

		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(file);
			workbook.write(outputStream);
			outputStream.flush();
			System.gc(); // XSSFWORKBOOK can cause memory issue
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	private void createBody(XSSFSheet sheet, int rowCount, List<Map<String, Object>> rowsList,
			List<String> columnList) {
		SimpleDateFormat sf = new SimpleDateFormat("dd/MMMM/YYYY");
		int cellCount = 0;
		for (Map<String, Object> row : rowsList) {
			XSSFRow xssRow = sheet.createRow(++rowCount);
			Cell bodyCell = null;
			for (String column_name : columnList) {
				bodyCell = xssRow.createCell(cellCount);
				String value = row.get(column_name) instanceof Date ? sf.format(row.get(column_name))
						: String.valueOf(row.get(column_name));
				bodyCell.setCellValue(value);
				cellCount++;
			}
			cellCount = 0;
		}

	}

	private void createHeaderRow(XSSFWorkbook workbook, XSSFSheet sheet, int row, List<String> columnList) {
		CellStyle cs = workbook.createCellStyle();
		cs.setWrapText(true);

		XSSFRow xssRow = sheet.createRow(row);
		Cell headerCell = null;
		int cellCount = 0;

		for (String column_name : columnList) {
			headerCell = xssRow.createCell(cellCount);
			headerCell.setCellValue(column_name.toUpperCase());
			sheet.setColumnWidth(cellCount, poiWidth(24.0));
			cellCount++;
		}

		row++;
	}

	private int poiWidth(double width) {
		return (int) Math.round(width * 256 + 200);
	}
}
