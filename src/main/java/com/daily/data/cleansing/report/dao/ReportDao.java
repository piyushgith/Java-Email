package com.daily.data.cleansing.report.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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

		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
		Calendar c1 = Calendar.getInstance();
		Date date = c1.getTime();
		int currentday = c1.get(Calendar.DAY_OF_WEEK);
		System.out.println(new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime()) + "  is day "
				+ currentday + "  of week");

		if (currentday != 2) {
			String endDate = sdf.format(date);
			SQL = SQL.replace("####", endDate);

			c1.setTime(date);
			c1.add(Calendar.DATE, -1);
			date = c1.getTime();
			String startDate = sdf.format(date);
			SQL = SQL.replace("$$$$", startDate);
			System.out.println(startDate + "======" + endDate);
		} else {

			String endDate = sdf.format(date);
			SQL = SQL.replace("####", endDate);

			c1.setTime(date);
			c1.add(Calendar.DATE, -3);// if Monday then 3 days back
			date = c1.getTime();
			String startDate = sdf.format(date);
			SQL = SQL.replace("$$$$", startDate);
			System.out.println(startDate + "======" + endDate);
		}

		List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL);

		return rows.size() > 0 ? rows : null;
 }
}
/*		//sql.query=SELECT TSGRoster.email, RE.id, concat(RE.assetPrefix, RE.assetNumber) as Asset, 
		RE.rampEventType, RE.loadStatus, RE.status, RE.createDate, RE.updateDate, RE.originalID 
		FROM operational.ResolvedEvent RE JOIN csg_database.TSGRoster 
		ON TSGRoster.email = RE.createUser AND TSGRoster.onsite = 0 
		WHERE RE.eventAssetSubclass = 'CONTAINER' AND 
		RE.createDate >= (DATE(NOW())-$) AND RE.createDate <= DATE(NOW()) order by RE.createDate asc ;*/
