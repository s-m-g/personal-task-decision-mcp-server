package com.mcp.personal_task_decision_mcp_server.tasks;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
public class TaskService {

	private final List<Task> tasks = new CopyOnWriteArrayList<>();

	public Task addTask(String title, int urgency, LocalDate dueDate) {
		Task task = new Task(title, urgency, dueDate);
		tasks.add(task);
		return task;
	}

	public List<Task> getAllTasks() {
		return tasks.stream().filter(task -> task.getStatus().equals("DUE")).collect(Collectors.toList());
	}

	public Task completeTask(String id) {
		Task targetTask = null;
		Optional<Task> queriedTask = tasks.stream().filter(task -> task.getId().equalsIgnoreCase(id)).findFirst();
		if(queriedTask.isPresent()) {
			targetTask = queriedTask.get();
		}else {
			return null;
		}
		if(targetTask!=null) {
			targetTask.setStatus("COMPLETED");
		}
		return targetTask;
	}
}
