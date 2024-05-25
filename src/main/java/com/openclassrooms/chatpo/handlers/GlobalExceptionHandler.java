package com.openclassrooms.chatpo.handlers;

import com.openclassrooms.chatpo.exceptions.ObjectValidationException;
import com.openclassrooms.chatpo.exceptions.OperationNonPermittedException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ObjectValidationException.class)
    public ResponseEntity<ExceptionRepresentation> handleException(ObjectValidationException e) {
        //log
        ExceptionRepresentation representation = ExceptionRepresentation.builder()
                .errorMessage("Object not valid exception has occured")
                .errorSource(e.getViolationSource())
                .validationErrors(e.getViolations())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(representation);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionRepresentation> handleException(EntityNotFoundException e) {
        ExceptionRepresentation representation = ExceptionRepresentation.builder()
                .errorMessage(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(representation);
    }


    @ExceptionHandler(OperationNonPermittedException.class)
    public ResponseEntity<ExceptionRepresentation> handleException(OperationNonPermittedException e) {
        ExceptionRepresentation representation = ExceptionRepresentation.builder()
                .errorMessage(e.getErrorMsg())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(representation);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionRepresentation> handleException(DataIntegrityViolationException e) {
        ExceptionRepresentation representation = ExceptionRepresentation.builder()
                .errorMessage(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(representation);
    }
}
