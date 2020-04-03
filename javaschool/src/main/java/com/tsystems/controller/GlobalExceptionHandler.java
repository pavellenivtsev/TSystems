package com.tsystems.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);

    /**
     * Default handler of all exceptions
     *
     * @param e - exception
     * @return view
     */
    @ExceptionHandler(value = Exception.class)
    public String defaultHandlerException(Exception e) {
        LOGGER.error("Unknown exception", e);
        return "error/error";
    }
}
