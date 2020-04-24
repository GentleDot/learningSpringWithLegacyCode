package net.gentledot.springcodeproject.errors;

public class TransactionFailException extends RuntimeException {
    public TransactionFailException(String message, Class targetClass) {
        super(String.format(message + "(대상 domain : %s)", targetClass.getName()));
    }
}
