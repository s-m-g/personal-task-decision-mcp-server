package com.mcp.personal_task_decision_mcp_server.tools;

import java.util.Map;

@FunctionalInterface	
public interface ToolHandler {

	Object handle(Map<String, Object> arguments);	
}
