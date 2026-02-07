package com.mcp.personal_task_decision_mcp_server.tools;

import java.util.Map;

public class ToolDefinition {

	private final String name;
	private final String description;
	private final Map<String, Object> inputSchema;

	public ToolDefinition(String name, String description, Map<String, Object> inputSchema) {
		this.name = name;
		this.description = description;
		this.inputSchema = inputSchema;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Map<String, Object> getInputSchema() {
		return inputSchema;
	}
}
