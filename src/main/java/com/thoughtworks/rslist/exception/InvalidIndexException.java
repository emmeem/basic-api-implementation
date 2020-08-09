package com.thoughtworks.rslist.exception;

import lombok.Data;

@Data
public class InvalidIndexException extends Throwable {
    public InvalidIndexException(String message) {
        super(message);
    }
}
