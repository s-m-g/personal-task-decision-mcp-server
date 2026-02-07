package com.mcp.personal_task_decision_mcp_server.protocol;

public class McpResponse {

    // Tool result (can be any shape)
    private Object result;

    public McpResponse(Object result) {
        this.result = result;
    }

    public Object getResult() {
        return result;
    }

    public static McpResponse of(Object result) {
        return new McpResponse(result);
    }
}
