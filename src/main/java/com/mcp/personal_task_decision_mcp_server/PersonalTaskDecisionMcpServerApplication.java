package com.mcp.personal_task_decision_mcp_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.mcp.personal_task_decision_mcp_server.tools.ToolRegistry;

@SpringBootApplication
public class PersonalTaskDecisionMcpServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonalTaskDecisionMcpServerApplication.class, args);
	}
	
	@Bean
	public ToolRegistry toolRegistry() {
		return new ToolRegistry();
	}

}
