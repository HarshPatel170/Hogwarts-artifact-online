package com.harsh.hogwartsartifactonline.system.exception;


import com.harsh.hogwartsartifactonline.artifact.ArtifactNotFoundException;
import com.harsh.hogwartsartifactonline.system.Result;
import com.harsh.hogwartsartifactonline.system.StatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(ArtifactNotFoundException.class)
    Result handleArtifactNotFoundException(ArtifactNotFoundException ex){
        return new Result(false, StatusCode.NOT_FOUND, ex.getMessage());
    }

}
