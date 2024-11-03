package com.polarbookshop.catalogservice.web;

import java.util.HashMap;
import java.util.Map;

import com.polarbookshop.catalogservice.domain.BookAlreadyExistsException;
import com.polarbookshop.catalogservice.domain.BookNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice//Отмечает класс как централизованный обработчик исключений.
public class BookControllerAdvice {

    @ExceptionHandler(BookNotFoundException.class)// Определяет исключение, для которого должен быть выполнен обработчик
    @ResponseStatus(HttpStatus.NOT_FOUND)//Определяет код состояния для ответа HTTP, созданного при возникновении исключения.
    String bookNotFoundHandler(BookNotFoundException ex) {
        return ex.getMessage();//Сообщение, которое будет включено в тело ответа HTTP.
    }

    @ExceptionHandler(BookAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    String bookAlreadyExistsHandler(BookAlreadyExistsException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {//Обрабатывает исключение, возникающее при сбое проверки книги(@Valid)
        var errors = new HashMap<String, String>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);//Собирает значимые сообщения об ошибках о том, какие поля книги недействительны, вместо возврата пустого сообщения.
        });
        return errors;
    }

}