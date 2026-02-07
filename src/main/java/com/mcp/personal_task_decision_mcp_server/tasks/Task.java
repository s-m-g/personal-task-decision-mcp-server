package com.mcp.personal_task_decision_mcp_server.tasks;

import java.util.Date;
import java.util.UUID;

public class Task {

	private final String id;
	private final String title;
	private final String due;
	private final Date createdAt;

	public Task(String title, String due) {
		this.id = UUID.randomUUID().toString();
		this.title = title;
		this.due = due;
		this.createdAt = new Date();
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDue() {
		return due;
	}

	public Date getCreatedAt() {
		return createdAt;
	}
}
