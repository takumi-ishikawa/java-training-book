package com.example.controller.filter;

import com.example.model.UserName;
import com.example.model.UserRepository;
import com.example.model.UserToken;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenFilter extends OncePerRequestFilter {

  private Logger logger = LoggerFactory.getLogger(TokenFilter.class);
  private final UserRepository userRepository;

  @Contract(pure = true)
  public TokenFilter(@NotNull final UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    final String userToken = request.getHeader("X-USER-TOKEN");
    final String uri = request.getRequestURI();
    final String[] splitedUri = uri.split("/");
    final String userName = splitedUri[2];
    if (!request.getMethod().equals("GET")) {
      userRepository.findUserByUserNameAndUserToken(UserName.of(userName), UserToken.of(userToken));
    }
    filterChain.doFilter(request, response);
  }
}
