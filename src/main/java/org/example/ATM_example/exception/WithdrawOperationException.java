package org.example.ATM_example.exception;

public class WithdrawOperationException extends RuntimeException {
    public WithdrawOperationException(String message) {
        super(message);
    }
}
