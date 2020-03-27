package org.covid19.contactbase.controller;

import org.covid19.contactbase.response.ActionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlingController {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlingController.class);

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ActionResponse handleErrors(Throwable e) {
        logger.error("Error occurred ", e);
        return new ActionResponse(false, e.getMessage());
    }
}