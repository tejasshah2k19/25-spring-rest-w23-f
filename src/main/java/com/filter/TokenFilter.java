package com.filter;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//controller repository service component
@Component
public class TokenFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) (request);

		String url = req.getRequestURI();
		// api/admin
		// api/customer
		//
		boolean flag = true;
		if (url.contains("/public/")) {
			System.out.println("public url");
		} else {
			// header token -> 123 ->
			String token = req.getHeader("token");
			System.out.println("private url" + token);

			//
			if (token == null || !token.equals("123")) {
				// un autho
				flag = false;
			}

		}

		if (flag == true) {
			chain.doFilter(request, response);// go ahead...
		} else {
			
			HttpServletResponse rp = (HttpServletResponse)response;
			
			rp.setContentType("application/json");
			rp.setCharacterEncoding("UTF-8");
			HashMap<String, Object> map = new HashMap<>();
			map.put("msg", "Please Login Before access the service");
			ObjectMapper mapper = new ObjectMapper();
			String resp = mapper.writeValueAsString(map);
			rp.setStatus(HttpStatus.UNAUTHORIZED.value());
			rp.getWriter().write(resp);
		}
	}
}
