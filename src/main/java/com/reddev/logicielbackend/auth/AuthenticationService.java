package com.reddev.logicielbackend.auth;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.reddev.logicielbackend.config.JwtService;
import com.reddev.logicielbackend.exception.HttpHandler.NotAcceptableException;
import com.reddev.logicielbackend.exception.HttpHandler.NotfoundException;
import com.reddev.logicielbackend.exception.HttpHandler.UnauthorizedException;
import com.reddev.logicielbackend.model.token.Token;
import com.reddev.logicielbackend.model.token.TokenType;
import com.reddev.logicielbackend.model.user.User;
import com.reddev.logicielbackend.repository.TokenRepository;
import com.reddev.logicielbackend.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.NotActiveException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationService {
  private final UserRepository userRepository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  public AuthenticationResponse register(Requests request) throws NotAcceptableException {

    checkIfEmailExists(request.getEmail());

    var user = User.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .phoneNumber(request.getPhoneNumber())
                    .role(request.getRole())
                    .locked(false)
                    .enabled(true)
                    .build();


    var savedUser = userRepository.save(user);
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);

    saveUserToken(savedUser, jwtToken);

    return AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .refreshToken(refreshToken)
            .build();
  }

  public AuthenticationResponse login(AuthenticationRequest request) throws UnauthorizedException, NotfoundException, NotAcceptableException, NotActiveException {
    boolean userExists = userRepository
            .findByEmail(request.getEmail())
            .isPresent();

    if (!userExists) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User no found!");
    }


    var user = userRepository.findByEmail(request.getEmail())
            .orElseThrow();

    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);

    if (!user.isEnabled()) {
      throw new NotAcceptableException(jwtToken);
    }

    if (!user.isAccountNonLocked()) {
      throw new UnauthorizedException("Account Deactivated");
    }

    try {
      passwordEncoder.matches(passwordEncoder.encode(request.getPassword()), user.getPassword());

    } catch (Exception e) {
      throw new UnauthorizedException("Bad Credentials");
    }

    user.setLastLogin(LocalDateTime.now());
    userRepository.save(user);

    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);

    return AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .refreshToken(refreshToken)
            .build();
  }


  public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userEmail;

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return;
    }

    refreshToken = authHeader.substring(7);
    userEmail = jwtService.extractUsername(refreshToken);
    if (userEmail != null) {
      var user = this.userRepository.findByEmail(userEmail)
              .orElseThrow();
      if (jwtService.isTokenValid(refreshToken, user)) {
        var accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        var authResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }


  private void revokeAllUserTokens(User user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }


  private void saveUserToken(User user, String jwtToken) {
    var token = Token.builder()
            .user(user)
            .token(jwtToken)
            .tokenType(TokenType.BEARER)
            .expired(false)
            .revoked(false)
            .build();
    tokenRepository.save(token);
  }

  public void checkIfEmailExists(String email) throws NotAcceptableException {
    if (userRepository.findByEmail(email).isPresent()) {
      // Email already exists, throw an exception or handle it as needed
      throw new NotAcceptableException("Email already exists: " + email);
    }
  }

}