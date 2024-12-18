package com.example.receiptprocessor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ReceiptNotFoundException extends RuntimeException {
    public ReceiptNotFoundException(String receiptId) {
        super("Receipt not found: " + receiptId);
    }
}