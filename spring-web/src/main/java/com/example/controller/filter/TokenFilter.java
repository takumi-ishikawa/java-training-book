package com.example.controller.filter;

import com.example.model.NoResourceException;
import com.example.model.UserName;
import com.example.model.UserRepository;
import com.example.model.UserToken;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class TokenFilter extends OncePerRequestFilter {

  private Logger logger = LoggerFactory.getLogger(TokenFilter.class);
  private final UserRepository userRepository;
  private Pattern USER_NAME_PATTERN = Pattern.compile("(?<=^/users/)[a-zA-Z0-9]+");

  @Contract(pure = true)
  public TokenFilter(@NotNull final UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  protected void doFilterInternal(
      final HttpServletRequest request,
      final HttpServletResponse response,
      final FilterChain filterChain)
      throws ServletException, IOException {
    final String userToken = request.getHeader("X-USER-TOKEN");
    final Matcher userNameMatcher = USER_NAME_PATTERN.matcher(request.getRequestURI());
    if (request.getMethod().equals("POST")) {
      filterChain.doFilter(request, response);
      return;
    }
    if (request.getMethod().equals("GET")) {
      filterChain.doFilter(request, response);
      return;
    }
    if (userNameMatcher.find()) {
      final String userName = userNameMatcher.group();
      userRepository.findUserByUserNameAndUserToken(UserName.of(userName), UserToken.of(userToken));
    } else {
      throw new NoResourceException("failed to find name");
    }
    filterChain.doFilter(request, response);
  }
}
