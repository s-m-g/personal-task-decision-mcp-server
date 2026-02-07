package com.mcp.personal_task_decision_mcp_server.tasks;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class TaskService {

	private final List<Task> tasks = new CopyOnWriteArrayList<>();

	public Task addTask(String title, String due) {
		Task task = new Task(title, due);
		tasks.add(task);
		return task;
	}

	public List<Task> getAllTasks() {
		return tasks;
	}
}
