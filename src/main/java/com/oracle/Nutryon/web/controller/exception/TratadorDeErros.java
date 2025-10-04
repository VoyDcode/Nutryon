package com.oracle.nutryon.web.controller.exception;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class TratadorDeErros {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String invalidData(MethodArgumentNotValidException ex){
    return "Dados invalidos: " + ex.getBindingResult().getErrorCount() + " erro(s).";
  }

  @ExceptionHandler(java.util.NoSuchElementException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public String notFound(){ return "Recurso nao encontrado."; }
}
