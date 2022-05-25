package com.tectoro.Filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tectoro.JwtUtil;
@Component
public class SecurityFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private UserDetailsService detailsService;
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain)
			throws ServletException, IOException 
	{
		//1.read token from Authorization head
		
		String token = request.getHeader("Authorization");
		if(token!=null)
		{
			//do validation
			String username = jwtUtil.getUsername(token);
			if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
			{
			 UserDetails usr = detailsService.loadUserByUsername(username);
			 //validate token
			 
			 boolean isvalid = jwtUtil.validateToken(token, usr.getUsername());
			 if(isvalid)
			 {
				 UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(username, usr.getPassword(),usr.getAuthorities());
				 authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				 //final object stored in securityContext with user details(un,pwd)
				 SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			 }
			 
			}
			
		}
		filterChain.doFilter(request, response);
	}
	

}
