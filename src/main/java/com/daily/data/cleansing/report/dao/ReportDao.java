package com.daily.data.cleansing.report.dao;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ReportDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	// private static final String SQL = "select * from calldetails";

	@Value("${sql.query}")
	private String SQL;

	public List<Map<String, Object>> findAll() {

		List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL);

		return rows.size() > 0 ? rows : null;
	}

}
