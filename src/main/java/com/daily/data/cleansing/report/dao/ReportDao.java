package com.daily.data.cleansing.report.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.daily.data.cleansing.report.model.ReportBean;

@Repository
public class ReportDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	//private static final String SQL = "select * from calldetails";
	
	@Value("${sql.query}")
	private String SQL;
	

	public List<ReportBean> findAll() {

		List<ReportBean> reportList = new ArrayList<ReportBean>();
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL);

		for (Map<String, Object> row : rows) {

			ReportBean reportBean = new ReportBean();

			reportBean.setId((int) row.get("id"));
			reportBean.setCalled_by((Long) row.get("called_by"));
			reportBean.setCalled_to((Long) row.get("called_to"));
			reportBean.setCalled_on((Date) row.get("called_on"));
			reportBean.setDuration((int) row.get("duration"));
			reportList.add(reportBean);
		}
		return reportList;
	}

}
