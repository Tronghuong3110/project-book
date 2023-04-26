package com.bookstore.security;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bookstore.service.impl.UserDetailService;


public class JwtTokenFilter extends OncePerRequestFilter{
	private static final Logger log = LoggerFactory.getLogger(JwtTokenFilter.class);

	@Autowired
	private JwtProvider jwtProvider;
	@Autowired
	private UserDetailService userDetailService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, 
									HttpServletResponse response,
									FilterChain filterChain) throws ServletException, IOException {
		try {
			String token = getToken(request);
			// ktra chuoi jwt(token) co hop le khong
			if(token != null && jwtProvider.validateToken(token)) {
				// get User Name form token 
				String userName = jwtProvider.getUserNameFromToken(token);
				 // load user by userName form database
				UserDetails userDetails = userDetailService.loadUserByUsername(userName);
				
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
																			userDetails,
																			null,
																			userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}
		catch (Exception e) {
			log.error("Can't set user authentication -> Message: {}", e);
		}
		
		filterChain.doFilter(request, response);
	}
	
	private String getToken(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		
		// ktra header Authorization co chua JWT khong
		if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer")) {
			return authHeader.replace("Bearer", "");
		}
		return null;
	}
}
