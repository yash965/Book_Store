package com.Yash.Book_Store.Filter;

import com.Yash.Book_Store.Service.JwtService;
import com.Yash.Book_Store.Service.User_Service;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// What this script does
// This filter reads a JWT from the request header, validates it, and — if valid — places an Authentication object into
// SecurityContextHolder so Spring Security knows the request is authenticated for downstream code.

public class JwtAuthFilter extends OncePerRequestFilter
{
    @Autowired
    JwtService jwtService;

    @Autowired
    User_Service userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authentication");
        String token = null;
        String username = null;

        if(authHeader != null && authHeader.startsWith("Bearer "))
        {
            token = authHeader.substring(7);
            username = jwtService.extractUsername(token);   // extract username from jwt token.
        }

/*SecurityContextHolder: per-thread storage where Spring holds authentication info for the request.
UsernamePasswordAuthenticationToken: a token representing a successful authentication (principal + authorities).
WebAuthenticationDetailsSource: adds request details (remote IP, session id) to the auth token.
OncePerRequestFilter: ensures this logic runs only once per request.*/

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null)
        {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if(jwtService.validateToken(token, userDetails))
            {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
