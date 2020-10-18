package com.example.routing.model.exception;

public abstract class BaseNotFoundException extends RuntimeException {
  @Override
  public abstract String getMessage();
}
