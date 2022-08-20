package com.signature.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.signature.exception.ResourceNotFoundException;
import com.signature.exception.ResourceUpdateFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  private final ObjectMapper objectMapper = new ObjectMapper();

  private ObjectNode getMessage(final Exception ex) {
    ObjectNode objectNode = objectMapper.createObjectNode();
    objectNode.put("message", ex.getMessage());
    return objectNode;
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<?> handleNotFoundException(ResourceNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(getMessage(ex));
  }

  @ExceptionHandler(ResourceUpdateFailedException.class)
  public ResponseEntity<?> handleUpdateFailedException(ResourceUpdateFailedException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getMessage(ex));
  }
}