package com.mcp.personal_task_decision_mcp_server.tools.init;

import com.mcp.personal_task_decision_mcp_server.tasks.Task;
import com.mcp.personal_task_decision_mcp_server.tasks.TaskService;
import com.mcp.personal_task_decision_mcp_server.tools.ToolDefinition;
import com.mcp.personal_task_decision_mcp_server.tools.ToolRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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

		ToolDefinition addTaskToolDefinition = new ToolDefinition("add_task", "Adds a task with its urgency level (from 1 to 5, 1 being least urgent)  and due date (format : yyyy-MM-dd) , to the user's personal task list",
				Map.of("title", Map.of("type", "string", "required", true),
						"urgency", Map.of("type", "int", "required", true),
						"dueDate", Map.of("type", "date", "required", true)
						));
		
		toolRegistry.register(addTaskToolDefinition,
				args -> {
					String title = (String) args.get("title");
					int urgency = Integer.valueOf((String) args.get("urgency"));
					
					DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					LocalDate dueDate = LocalDate.parse((String) args.get("dueDate"), format);
					

					Task task = taskService.addTask(title, urgency, dueDate);

					return Map.of("taskId", task.getId(), "title", task.getTitle(), "urgency", task.getUrgency(), "status",
							task.getStatus(), "deuDate", task.getDueDate());
				});
		
		//--------------------------------------------------------------------------------------------------------------
		ToolDefinition listTaskToolDefinition = new ToolDefinition("list_tasks", "Gets the user's personal task list",
				Map.of());
		
		toolRegistry.register(listTaskToolDefinition,
				args -> {
					return taskService.getAllTasks();
				});
		
		//--------------------------------------------------------------------------------------------------------------
		ToolDefinition completeTaskToolDefinition = new ToolDefinition("complete_task", "Marks the task as Completed",
				Map.of("taskId", Map.of("type", "string", "required", true)));
		toolRegistry.register(completeTaskToolDefinition, 
				args ->{
					Task task = taskService.completeTask((String)args.get("taskId"));
					if(task==null) {
						return Map.of("message", "Given task does not exists");
					}
					return Map.of("taskId", task.getId(), "title", task.getTitle(), "status", task.getStatus());
				});
	}
}
