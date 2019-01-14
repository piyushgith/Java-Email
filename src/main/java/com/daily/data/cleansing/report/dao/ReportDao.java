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

	// private static final String SQL = "select * from calldetails";

	@Value("${sql.query}")
	private String SQL;

	public List<ReportBean> findAll() {

		List<ReportBean> reportList = new ArrayList<ReportBean>();

		List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL);

		for (Map<String, Object> row : rows) {
			// row.forEach((K, V) -> { System.out.println(V.toString());});
			ReportBean reportBean = new ReportBean();
			reportBean.setId((Long) row.get("id"));
			reportBean.setEmail((String) row.get("email"));
			reportBean.setAsset((String) row.get("Asset"));
			reportBean.setCreateDate((Date) row.get("createDate"));
			reportBean.setLoadStatus((String) row.get("loadStatus"));
			reportBean.setOriginalID((Long) row.get("originalID"));
			reportBean.setRampEventType((String) row.get("rampEventType"));
			reportBean.setStatus((String) row.get("status"));
			reportBean.setUpdateDate((Date) row.get("updateDate"));

			reportList.add(reportBean);
		}
		return reportList;
	}

}
