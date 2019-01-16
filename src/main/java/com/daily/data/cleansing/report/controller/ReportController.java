package com.daily.data.cleansing.report.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.daily.data.cleansing.report.service.ReportExcelGenerationService;

@RestController
// @RequestMapping("")
@CrossOrigin(origins = "http://localhost:9999")
public class ReportController {

	@Autowired
	private ReportExcelGenerationService reportExcelGenerationService;

	@RequestMapping("/")
	public String healthCheck() {
		return "Hi there !!";
	}

	@RequestMapping(value = "/result", method = RequestMethod.GET)
	public String getData() {
		return reportExcelGenerationService.findDetails();
	}

}
