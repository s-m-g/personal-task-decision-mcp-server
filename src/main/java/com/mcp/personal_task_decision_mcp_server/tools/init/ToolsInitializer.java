package com.mcp.personal_task_decision_mcp_server.tools.init;

import com.mcp.personal_task_decision_mcp_server.tasks.Task;
import com.mcp.personal_task_decision_mcp_server.tasks.TaskService;
import com.mcp.personal_task_decision_mcp_server.tools.ToolDefinition;
import com.mcp.personal_task_decision_mcp_server.tools.ToolRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

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
						"urgency", Map.of("type", "integer", "required", true),
						"dueDate", Map.of("type", "string", "format", "date", "required", true)
						));
		
		toolRegistry.register(addTaskToolDefinition,
				args -> {
					String title = (String) args.get("title");
					int urgency = ((Number) args.get("urgency")).intValue();
					String userId = (String) RequestContextHolder
					        .currentRequestAttributes()
					        .getAttribute("userId", RequestAttributes.SCOPE_REQUEST);
					
					DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					LocalDate dueDate = LocalDate.parse((String) args.get("dueDate"), format);
					

					Task task = taskService.addTask(title, urgency, dueDate, userId);

					return Map.of("taskId", task.getId(), "title", task.getTitle(), "urgency", task.getUrgency(), "status",
							task.getStatus(), "dueDate", task.getDueDate().toString());
				});
		
		//--------------------------------------------------------------------------------------------------------------
		ToolDefinition listTaskToolDefinition = new ToolDefinition("list_tasks", "Gets the user's personal task list",
				Map.of());
		
		toolRegistry.register(listTaskToolDefinition,
				args -> {
					String userId = (String) RequestContextHolder
					        .currentRequestAttributes()
					        .getAttribute("userId", RequestAttributes.SCOPE_REQUEST);
					return taskService.getAllTasks(userId)
					        .stream()
					        .map(task -> Map.of(
					                "taskId", task.getId(),
					                "title", task.getTitle(),
					                "urgency", String.valueOf(task.getUrgency()),
					                "dueDate", task.getDueDate().toString(),
					                "status", task.getStatus().toString()
					        ))
					        .toList();
				});
		
		//--------------------------------------------------------------------------------------------------------------
		ToolDefinition completeTaskToolDefinition = new ToolDefinition("complete_task", "Marks the task as Completed",
				Map.of("taskId", Map.of("type", "string", "required", true)));
		toolRegistry.register(completeTaskToolDefinition, 
				args ->{
					String userId = (String) RequestContextHolder
					        .currentRequestAttributes()
					        .getAttribute("userId", RequestAttributes.SCOPE_REQUEST);
					Task task = taskService.completeTask((String)args.get("taskId"), userId);
					if(task==null) {
						return Map.of("message", "Given task does not exists");
					}
					return Map.of("taskId", task.getId(), "title", task.getTitle(), "status", task.getStatus());
				});
		
		//-------------------------------------------------------------------------------------------------------------
		
		ToolDefinition suggestTaskTool =
		        new ToolDefinition(
		                "suggest_next_task",
		                "Suggests the most important pending task based on urgency and due date",
		                Map.of()
		        );
		
		toolRegistry.register(suggestTaskTool, args -> {
			String userId = (String) RequestContextHolder
			        .currentRequestAttributes()
			        .getAttribute("userId", RequestAttributes.SCOPE_REQUEST);
		    Task task = taskService.suggestNextTask(userId);

		    if (task == null) {
		        return Map.of("message", "No pending tasks");
		    }

		    return Map.of(
		            "taskId", task.getId(),
		            "title", task.getTitle(),
		            "urgency", task.getUrgency(),
		            "dueDate", task.getDueDate().toString(),
		            "status", task.getStatus().toString()
		    );
		});

	}
}
