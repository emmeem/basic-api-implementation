package com.thoughtworks.rslist.exception;

import lombok.Data;

@Data
public class InvalidIndexException extends Exception {

    public InvalidIndexException(String message) {
        super(message);
    }
}
