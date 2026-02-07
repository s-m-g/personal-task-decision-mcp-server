package com.mcp.personal_task_decision_mcp_server.tools;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ToolRegistry {

	private final Map<String, RegisteredTool> tools = new ConcurrentHashMap<>();

	public void register(ToolDefinition definition, ToolHandler handler) {
		tools.put(definition.getName(), new RegisteredTool(definition, handler));
	}

	public Collection<ToolDefinition> listTools() {
		return tools.values().stream().map(RegisteredTool::definition).toList();
	}

	public Object call(String toolName, Map<String, Object> arguments) {
		RegisteredTool tool = tools.get(toolName);
		if (tool == null) {
			throw new IllegalArgumentException("Unknown tool: " + toolName);
		}
		return tool.handler().handle(arguments);
	}

	private record RegisteredTool(ToolDefinition definition, ToolHandler handler) {
	}
}
