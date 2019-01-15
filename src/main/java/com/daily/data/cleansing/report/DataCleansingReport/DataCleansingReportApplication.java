package com.daily.data.cleansing.report.DataCleansingReport;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import com.daily.data.cleansing.report.service.ReportExcelGenerationService;

@SpringBootApplication(scanBasePackages = "com.daily.data.cleansing.report")
public class DataCleansingReportApplication {

	@Autowired
	private ReportExcelGenerationService reportExcelGenerationService;

	private Logger log = Logger.getLogger(this.getClass());

	public static void main(String[] args) {
		SpringApplication.run(DataCleansingReportApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class) //this will initiate this method once server is started
	public void doSomethingAfterStartup() {
		log.info("Calling DB to create Excel sheet");
		reportExcelGenerationService.sendEmail();
		log.info("Excel sheet has been generated");
	}

}
