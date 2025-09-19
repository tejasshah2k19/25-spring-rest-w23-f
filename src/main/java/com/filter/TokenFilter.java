package com.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

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
			System.out.println("private url"+token);

			//
			if (token == null || !token.equals("123")) {
				// un autho
				flag = false;
			}

		}

		if (flag == true) {
			chain.doFilter(request, response);// go ahead...
		}else {
			
		}
	}
}
