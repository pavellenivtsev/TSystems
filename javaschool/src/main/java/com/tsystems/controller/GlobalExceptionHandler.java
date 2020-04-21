package com.tsystems.controller;

import com.tsystems.exception.DataChangingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);

    /**
     * Handler for data changing exception
     *
     * @param request request
     * @param e exception
     * @param model model
     * @return error/405.jsp
     */
    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(value = DataChangingException.class)
    public String getDataChangingExceptionPage(HttpServletRequest request, Exception e, Model model) {
        LOGGER.warn("Request: " + request.getRequestURL() + " raised " + e);
        model.addAttribute("msg", e.getMessage());
        return "error/405";
    }

//    /**
//     * Default handler for all exceptions
//     *
//     * @param e - exception
//     * @return error/error.jsp
//     */
//    @ExceptionHandler(value = Exception.class)
//    public String defaultHandlerException(HttpServletRequest request, Exception e) {
//        LOGGER.error("Request: " + request.getRequestURL() + " raised " + e);
//        return "error/error";
//    }
}
