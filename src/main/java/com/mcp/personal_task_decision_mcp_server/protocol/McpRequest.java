package com.mcp.personal_task_decision_mcp_server.protocol;

import java.util.Map;

public class McpRequest {

    // MCP message type: "list_tools", "call_tool"
    private String type;

    // Tool name (only for call_tool)
    private String tool;

    // Arbitrary JSON arguments
    private Map<String, Object> arguments;

    public McpRequest() {
    }

    public String getType() {
        return type;
    }

    public String getTool() {
        return tool;
    }

    public Map<String, Object> getArguments() {
        return arguments;
    }
}
