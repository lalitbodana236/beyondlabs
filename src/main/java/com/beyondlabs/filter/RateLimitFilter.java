package com.beyondlabs.filter;

import com.beyondlabs.controller.RateBucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitFilter extends OncePerRequestFilter {
    
    private static final int LIMIT = 20;
    private static final long WINDOW_MS = 60_000;
    
    private final Map<String, RateBucket> buckets = new ConcurrentHashMap<>();
    
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {
        
        String userId = resolveUserId(request);
        
        if (userId != null) {
            RateBucket bucket = buckets.computeIfAbsent(
                    userId,
                    k -> new RateBucket(System.currentTimeMillis())
            );
            
            if (!bucket.allow(LIMIT, WINDOW_MS)) {
                response.setStatus(429);
                response.setContentType("application/json");
                response.getWriter().write("""
                {
                  "error": "RATE_LIMIT_EXCEEDED",
                  "message": "Too many requests. Please try again later."
                }
                """);
                return;
            }
        }
        
        chain.doFilter(request, response);
    }
    
    private String resolveUserId(HttpServletRequest request) {
        return request.getParameter("userId"); // simple + explicit
    }
}

