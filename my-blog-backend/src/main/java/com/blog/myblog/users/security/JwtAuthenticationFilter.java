package com.blog.myblog.users.security;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
  private final UserDetailsServiceImpl userDetailsServiceImple;
  private final JwtTokenProvider tokenProvider;

  public JwtAuthenticationFilter(UserDetailsServiceImpl userDetailsServiceImple, JwtTokenProvider tokenProvider) {
      this.userDetailsServiceImple = userDetailsServiceImple;
      this.tokenProvider = tokenProvider;
  }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getJwtFromRequest(request);

        if (token != null && tokenProvider.validateToken(token)) {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(tokenProvider.getSecret())).build().verify(token);
            String mail = decodedJWT.getClaim("mail").asString();

            UserDetails userDetails = userDetailsServiceImple.loadUserByUsername(mail);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, new ArrayList<>()));
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}
