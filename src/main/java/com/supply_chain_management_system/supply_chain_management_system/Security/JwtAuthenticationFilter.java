package com.supply_chain_management_system.supply_chain_management_system.Security;

import com.supply_chain_management_system.supply_chain_management_system.Service.CustomUserDetailsService;
import com.supply_chain_management_system.supply_chain_management_system.Service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            CustomUserDetailsService userDetailsService) {

        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("=================================");
        System.out.println("JWT FILTER EXECUTED");
        System.out.println("REQUEST: " + request.getRequestURI());

        String authHeader =
                request.getHeader("Authorization");

        if (authHeader == null) {

            System.out.println("AUTH HEADER: NOT FOUND");

        } else {

            System.out.println("AUTH HEADER: FOUND");
        }

        if (authHeader != null
                && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7);

            try {

                String email =
                        jwtService.extractUsername(token);

                System.out.println(
                        "JWT EMAIL: " + email
                );

                if (email != null
                        && SecurityContextHolder
                        .getContext()
                        .getAuthentication() == null) {

                    UserDetails userDetails =
                            userDetailsService
                                    .loadUserByUsername(email);

                    if (jwtService.validateToken(
                            token,
                            userDetails)) {

                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails,
                                        null,
                                        userDetails.getAuthorities()
                                );

                        SecurityContextHolder
                                .getContext()
                                .setAuthentication(authentication);

                        System.out.println(
                                "JWT VALID - USER AUTHENTICATED"
                        );

                    } else {

                        System.out.println(
                                "JWT INVALID"
                        );
                    }
                }

            } catch (Exception e) {

                System.out.println(
                        "JWT ERROR: " + e.getMessage()
                );
            }
        }

        filterChain.doFilter(request, response);
    }
}