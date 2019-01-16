package com.daily.data.cleansing.report.model;

import java.util.Date;
//import org.springframework.stereotype.Component;

//@Component- Not required any more
public class ReportBean {

	private Long id;
	private String email;
	private String asset;
	private String rampEventType;
	private String loadStatus;
	private String status;
	private Date createDate;
	private Date updateDate;
	private Long originalID;

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getAsset() {
		return asset;
	}

	public String getRampEventType() {
		return rampEventType;
	}

	public String getLoadStatus() {
		return loadStatus;
	}

	public String getStatus() {
		return status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public Long getOriginalID() {
		return originalID;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAsset(String asset) {
		this.asset = asset;
	}

	public void setRampEventType(String rampEventType) {
		this.rampEventType = rampEventType;
	}

	public void setLoadStatus(String loadStatus) {
		this.loadStatus = loadStatus;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public void setOriginalID(Long originalID) {
		this.originalID = originalID;
	}

	@Override
	public String toString() {
		return "ReportBean [email=" + email + ",id=" + id + ", asset=" + asset + ", rampEventType=" + rampEventType
				+ ", loadStatus=" + loadStatus + ", status=" + status + ", createDate=" + createDate + ", updateDate="
				+ updateDate + ", originalID=" + originalID + "]";
	}

}
