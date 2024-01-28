package com.reddev.logicielbackend.auth;

import com.reddev.logicielbackend.exception.HttpHandler.NotAcceptableException;
import com.reddev.logicielbackend.exception.HttpHandler.NotfoundException;
import com.reddev.logicielbackend.exception.HttpHandler.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.NotActiveException;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService authenticationService;


  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(@RequestBody Requests request) throws Exception {
    return ResponseEntity.ok(authenticationService.register(request));
  }



  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) throws NotActiveException, NotfoundException, NotAcceptableException, UnauthorizedException {
    return ResponseEntity.ok(authenticationService.login(request));
  }


  @PostMapping("/refresh-token")
  public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
    authenticationService.refreshToken(request, response);
  }


  @GetMapping("/logout")
  public String logout(HttpServletRequest request) {
    request.getSession().invalidate();
    return "redirect:/login";
  }


}
