package com.mcp.personal_task_decision_mcp_server.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class McpSecurityFilter extends OncePerRequestFilter {

	@Value("${mcp.api.key}")
	private String serverKey;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String apiKey = request.getHeader("X-MCP-KEY");
		String userId = request.getHeader("X-USER-ID");

		if (apiKey == null || userId == null || !apiKey.equals(serverKey)) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.getWriter().write("Unauthorized MCP request");
			return;
		}

		// Attach userId to request context
		request.setAttribute("userId", userId);

		filterChain.doFilter(request, response);
	}
}
