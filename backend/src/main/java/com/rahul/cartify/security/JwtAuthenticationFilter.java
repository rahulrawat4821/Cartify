package com.rahul.cartify.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final CustomUserDetailsService
            customUserDetailsService;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            CustomUserDetailsService customUserDetailsService
    ) {

        this.jwtService = jwtService;
        this.customUserDetailsService =
                customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader =
                request.getHeader("Authorization");

        // DEBUG LOGS
        System.out.println(
                ">>> REQUEST URI: "
                        + request.getRequestURI()
        );

        System.out.println(
                ">>> REQUEST METHOD: "
                        + request.getMethod()
        );

        System.out.println(
                ">>> AUTH HEADER: "
                        + authHeader
        );

        // no token
        if (authHeader == null ||
                !authHeader.startsWith("Bearer ")) {

            filterChain.doFilter(
                    request,
                    response
            );

            return;
        }

        // remove "Bearer "
        String jwtToken =
                authHeader.substring(7);

        String userEmail = null;

        // handle expired/invalid token
        try {

            userEmail =
                    jwtService.extractUsername(
                            jwtToken
                    );

        } catch (Exception e) {

            System.out.println(
                    ">>> INVALID OR EXPIRED TOKEN"
            );

            filterChain.doFilter(
                    request,
                    response
            );

            return;
        }

        System.out.println(
                ">>> EMAIL FROM TOKEN: "
                        + userEmail
        );

        // if user not authenticated
        if (userEmail != null &&
                SecurityContextHolder
                        .getContext()
                        .getAuthentication() == null) {

            UserDetails userDetails =
                    customUserDetailsService
                            .loadUserByUsername(
                                    userEmail
                            );

            System.out.println(
                    ">>> AUTHORITIES: "
                            + userDetails.getAuthorities()
            );

            // validate token
            if (jwtService.isTokenValid(
                    jwtToken,
                    userDetails
            )) {

                System.out.println(
                        ">>> TOKEN VALID: true"
                );

                UsernamePasswordAuthenticationToken
                        authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authToken);

            } else {

                System.out.println(
                        ">>> TOKEN VALID: false"
                );
            }
        }

        filterChain.doFilter(
                request,
                response
        );
    }
}
// package com.rahul.cartify.security;

// import java.io.IOException;

// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
// import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;

// import jakarta.servlet.*;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// @Component
// public class JwtAuthenticationFilter extends OncePerRequestFilter {

//     private final JwtService jwtService;
//     private final CustomUserDetailsService customUserDetailsService;

//     public JwtAuthenticationFilter(JwtService jwtService,
//             CustomUserDetailsService customUserDetailsService) {
//         this.jwtService = jwtService;
//         this.customUserDetailsService = customUserDetailsService;
//     }

//     @Override
//     protected void doFilterInternal(HttpServletRequest request,
//             HttpServletResponse response,
//             FilterChain filterChain) throws ServletException, IOException {

//         final String authHeader = request.getHeader("Authorization");
        
//          // 👇 add these debug lines
//     System.out.println(">>> AUTH HEADER: " + authHeader);
//     System.out.println(">>> REQUEST URI: " + request.getRequestURI());
//     System.out.println(">>> REQUEST METHOD: " + request.getMethod());

//         if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//             filterChain.doFilter(request, response);
//             return;
//         }

//         String jwtToken = authHeader.substring(7);
//         String userEmail = jwtService.extractUsername(jwtToken);

//             // 👇 add these too
//     System.out.println(">>> EMAIL FROM TOKEN: " + userEmail);

//         if (userEmail != null &&
//                 SecurityContextHolder.getContext().getAuthentication() == null) {

//             UserDetails userDetails =
//                     customUserDetailsService.loadUserByUsername(userEmail);

//                // 👇 and this
//         System.out.println(">>> AUTHORITIES: " + userDetails.getAuthorities());

//         if (jwtService.isTokenValid(jwtToken, userDetails)) {
//             // set auth
//             System.out.println(">>> TOKEN VALID: true");
//         } else {
//             System.out.println(">>> TOKEN VALID: false");
//         }        


//             if (jwtService.isTokenValid(jwtToken, userDetails)) {

//                 UsernamePasswordAuthenticationToken authToken =
//                         new UsernamePasswordAuthenticationToken(
//                                 userDetails, null, userDetails.getAuthorities());

//                 authToken.setDetails(
//                         new WebAuthenticationDetailsSource().buildDetails(request));

//                 SecurityContextHolder.getContext().setAuthentication(authToken);
//             }
//         }

//         filterChain.doFilter(request, response);  // ✅ outside the if block
//     }
// }