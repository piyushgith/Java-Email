package com.daily.data.cleansing.report.model;

import java.math.BigInteger;
import java.util.Date;

import org.springframework.stereotype.Component;

//@Component
public class ReportBean {

	private int id;
	private Long called_by;
	private Long called_to;
	private Date called_on;
	private int duration;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Long getCalled_by() {
		return called_by;
	}

	public void setCalled_by(Long called_by) {
		this.called_by = called_by;
	}

	public Long getCalled_to() {
		return called_to;
	}

	public void setCalled_to(Long called_to) {
		this.called_to = called_to;
	}

	public Date getCalled_on() {
		return called_on;
	}

	public void setCalled_on(Date called_on) {
		this.called_on = called_on;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

}
