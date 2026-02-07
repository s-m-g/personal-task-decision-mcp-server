package com.mcp.personal_task_decision_mcp_server.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mcp.personal_task_decision_mcp_server.protocol.McpRequest;
import com.mcp.personal_task_decision_mcp_server.protocol.McpResponse;
import com.mcp.personal_task_decision_mcp_server.tools.ToolRegistry;

@RestController
@RequestMapping("/mcp")
public class McpController {

	private final ToolRegistry toolRegistry;

	public McpController(ToolRegistry toolRegistry) {
		this.toolRegistry = toolRegistry;
	}

	@PostMapping
	public McpResponse handle(@RequestBody McpRequest request) {
		
		String requestType = request.getType();
		if(requestType==null) {
			throw new IllegalArgumentException("Request type cannot be null");
		}
		
		final McpResponse mcpResponse;	
		switch (requestType) {
			case "list_tools":
				mcpResponse = McpResponse.of(toolRegistry.listTools());
				break;
			case "call_tool" :
				mcpResponse = McpResponse.of(toolRegistry.call(request.getTool(), request.getArguments()));
				break;
			default:
				throw new IllegalArgumentException("Unknown MCP request type: " + request.getType());
		}
		return mcpResponse;
	}

}
