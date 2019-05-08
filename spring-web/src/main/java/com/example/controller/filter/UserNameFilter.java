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
public class UserNameFilter extends OncePerRequestFilter {

  private Logger logger = LoggerFactory.getLogger(UserNameFilter.class);

  private static final int MAX_VALUE_LENGTH = 35;
  private static final int MIN_VALUE_LENGTH = 2;
  private static Pattern PATTERN = Pattern.compile("^[a-zA-Z]+[a-zA-Z0-9]*$");

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    final String uri = request.getRequestURI();
    final String[] splitedUri = uri.split("/");
    final String userName = splitedUri[splitedUri.length - 1];
    if (!request.getMethod().equals("GET")) {
      if (!validate(userName)) {
        logger.info("invalid request : path: {}, remote {}", request.getRequestURI(), request.getRemoteAddr());
        throw new IllegalArgumentException("invalid user name");
      }

    }
    filterChain.doFilter(request, response);
  }

  private boolean validate(String userName) {
    final Matcher matcher = PATTERN.matcher(userName);

    if (userName.length() <= MIN_VALUE_LENGTH) {
      return false;
    }
    if (userName.length() >= MAX_VALUE_LENGTH) {
      return false;
    }
    if (!matcher.find()) {
      return false;
    }
    return true;
  }
}
