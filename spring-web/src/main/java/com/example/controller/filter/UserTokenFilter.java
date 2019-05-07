package com.example.controller.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserTokenFilter extends OncePerRequestFilter {

  private Logger logger = LoggerFactory.getLogger(UserTokenFilter.class);

  private static final int MAX_VALUE_LENGTH = 127;
  private static final int MIN_VALUE_LENGTH = 8;
  private static final Pattern PATTERN = Pattern.compile("^[a-zA-Z0-9-]+$");

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    final String userToken = request.getHeader("X-USER-TOKEN");
    if (!validate(userToken)) {
      logger.info("invalid request : path: {}, remote {}", request.getRequestURI(), request.getRemoteAddr());
      throw new IllegalArgumentException("invalid user token");
    }
    filterChain.doFilter(request, response);
  }

  private boolean validate(String userToken) {
    final Matcher matcher = PATTERN.matcher(userToken);

    if (userToken.length() <= MIN_VALUE_LENGTH) {
      return false;
    }
    if (userToken.length() >= MAX_VALUE_LENGTH) {
      return false;
    }
    if (!matcher.find()) {
      return false;
    }
    return true;
  }
}
