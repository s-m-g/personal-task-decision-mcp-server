package com.mcp.personal_task_decision_mcp_server.tasks;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class Task {

	private final String id;
	private final String title;
	private final LocalDate createdAt;
	private final LocalDate dueDate;
	private String status;
	private final int urgency;
	private String userId;

	public Task(String title, int urgency, LocalDate dueDate, String userId) {
		this.id = UUID.randomUUID().toString();
		this.title = title;
		this.urgency = urgency;
		this.createdAt = LocalDate.now();
		this.status = "DUE";
		this.dueDate = dueDate;
		this.userId = userId;
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public int getUrgency() {
		return this.urgency;
	}
	
	public LocalDate getDueDate() {
		return this.dueDate;
	}
	
	public String getUserId() {
		return userId;
	}

//	public void setUserId(String userId) {
//		this.userId = userId;
//	}
}
