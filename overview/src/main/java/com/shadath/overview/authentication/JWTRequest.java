package com.shadath.overview.authentication;



import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shadath.overview.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JWTRequest extends OncePerRequestFilter {
    @Autowired
    private JWTToken jwtToken;
    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        UserDetails userDetails = null;
        String username = null, jwtToken = null;/*username & password should be in json format for authenticate*/
        final String requestTokenHeader = request.getHeader("Authorization");/*Under Header,Authorization: Bearer 9a02115a835ee03d5fb83cd8a468ea33e4090aaaec.87f53c9fa54512bbef4db8dc656c82a315fa0c785c08b013.4716b81ddcd0153d2a7556f2e154912cf5675f*/
        try {
            if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
                jwtToken = requestTokenHeader.substring(7);
                try {
                    username = this.jwtToken.getUsernameFromToken(jwtToken);
                }
                catch (IllegalArgumentException e) {
                    System.out.println("Unable to get JWT Token");
                }
                catch (ExpiredJwtException e) {
                    System.out.println("JWT Token has expired");
                }
                catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
            else {
                logger.warn("JWT Token does not begin with Bearer String");
            }
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                userDetails = this.userService.loadUserByUsername(username);
                if (this.jwtToken.validateToken(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
            chain.doFilter(request, response);
        }
        finally {
            userDetails = null;
            username = jwtToken = null;
        }
    }
}
