package com.reddev.logicielbackend.exception.HttpHandler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ResponseStatus(value = HttpStatus.OK)
public class Response {

  public HttpStatus status;
  public String message;
  public LocalDateTime time;

}