package com.mcp.personal_task_decision_mcp_server.tools;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.mcp.personal_task_decision_mcp_server.tasks.Task;
import com.mcp.personal_task_decision_mcp_server.tasks.TaskService;

import org.springframework.ai.mcp.server.annotation.McpTool;
import org.springframework.ai.mcp.server.annotation.McpToolParam;

@Component
public class TaskTools {

	private final TaskService taskService;

	public TaskTools(TaskService taskService) {
		this.taskService = taskService;
	}

	private String currentUserId() {
		return (String) RequestContextHolder.currentRequestAttributes()
				.getAttribute("userId", RequestAttributes.SCOPE_REQUEST);
	}

	@McpTool(name = "add_task", description = "Adds a task with its urgency level (1 to 5, 1 being least urgent) and due date (format: yyyy-MM-dd) to the user's personal task list.")
	public Map<String, Object> addTask(
			@McpToolParam(description = "Task title", required = true) String title,
			@McpToolParam(description = "Urgency from 1 (low) to 5 (high)", required = true) int urgency,
			@McpToolParam(description = "Due date in yyyy-MM-dd format", required = true) String dueDate) {

		String userId = currentUserId();

		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate parsedDueDate = LocalDate.parse(dueDate, format);

		Task task = taskService.addTask(title, urgency, parsedDueDate, userId);

		return Map.of(
				"taskId", task.getId(),
				"title", task.getTitle(),
				"urgency", task.getUrgency(),
				"status", task.getStatus(),
				"dueDate", task.getDueDate().toString());
	}

	@McpTool(name = "list_tasks", description = "Gets the user's personal task list.")
	public List<Map<String, Object>> listTasks() {
		String userId = currentUserId();

		return taskService.getAllTasks(userId)
				.stream()
				.map(task -> Map.of(
						"taskId", task.getId(),
						"title", task.getTitle(),
						"urgency", task.getUrgency(),
						"dueDate", task.getDueDate().toString(),
						"status", task.getStatus()))
				.collect(Collectors.toList());
	}

	@McpTool(name = "complete_task", description = "Marks a task as completed.")
	public Map<String, Object> completeTask(
			@McpToolParam(description = "The task identifier", required = true) String taskId) {

		String userId = currentUserId();
		Task task = taskService.completeTask(taskId, userId);

		if (task == null) {
			return Map.of("message", "Given task does not exists");
		}

		return Map.of(
				"taskId", task.getId(),
				"title", task.getTitle(),
				"status", task.getStatus());
	}

	@McpTool(name = "suggest_next_task", description = "Suggests the most important pending task based on urgency and due date.")
	public Map<String, Object> suggestNextTask() {
		String userId = currentUserId();
		Task task = taskService.suggestNextTask(userId);

		if (task == null) {
			return Map.of("message", "No pending tasks");
		}

		return Map.of(
				"taskId", task.getId(),
				"title", task.getTitle(),
				"urgency", task.getUrgency(),
				"dueDate", task.getDueDate().toString(),
				"status", task.getStatus());
	}
}

