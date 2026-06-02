package com.example.FinanceTracker.security;

import com.example.FinanceTracker.service.CustomerUserDetailsService;
import com.example.FinanceTracker.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtService jwtService;

    private CustomerUserDetailsService customerUserDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, CustomerUserDetailsService customerUserDetailsService) {
        this.jwtService = jwtService;
        this.customerUserDetailsService = customerUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterchain) throws ServletException, IOException {

        //System.out.println("JWT FILTER RUNNING");
        final String authHeader = request.getHeader("Authorization");
        //System.out.println(authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterchain.doFilter(request, response);
            return;
        }
        String jwtToken = authHeader.substring(7);
        String email = jwtService.extractEmail(jwtToken);
        //System.out.println(email);

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = customerUserDetailsService.loadUserByUsername(email);
            if (jwtService.validateToken(jwtToken, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

            }
        }
        filterchain.doFilter(request, response);
    }
}
