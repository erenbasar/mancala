package com.ebasar.mancala.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid Move")
public class InvalidMoveException extends Exception
{
    public InvalidMoveException(String message)
    {
        super(message);
    }
}
