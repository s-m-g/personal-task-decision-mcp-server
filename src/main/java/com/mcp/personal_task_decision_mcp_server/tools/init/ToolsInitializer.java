package com.mcp.personal_task_decision_mcp_server.tools.init;

import com.mcp.personal_task_decision_mcp_server.tasks.Task;
import com.mcp.personal_task_decision_mcp_server.tasks.TaskService;
import com.mcp.personal_task_decision_mcp_server.tools.ToolDefinition;
import com.mcp.personal_task_decision_mcp_server.tools.ToolRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ToolsInitializer {

	private final ToolRegistry toolRegistry;
	private final TaskService taskService;

	public ToolsInitializer(ToolRegistry toolRegistry, TaskService taskService) {
        this.toolRegistry = toolRegistry;
        this.taskService = taskService;
    }

	@PostConstruct
	public void registerTools() {

		ToolDefinition addTaskToolDefinition = new ToolDefinition("add_task", "Adds a task to the user's personal task list",
				Map.of("title", Map.of("type", "string", "required", true), "due", Map.of("type", "string", "required", false)));
		
		toolRegistry.register(addTaskToolDefinition,
				args -> {
					String title = (String) args.get("title");
					String due = (String) args.get("due");

					Task task = taskService.addTask(title, due);

					return Map.of("taskId", task.getId(), "title", task.getTitle(), "due", task.getDue(), "status",
							"added");
				});
		
		
		ToolDefinition listTaskToolDefinition = new ToolDefinition("list_tasks", "Gets the user's personal task list",
				Map.of());
		
		toolRegistry.register(listTaskToolDefinition,
				args -> {
					return taskService.getAllTasks();
				});
	}
}
