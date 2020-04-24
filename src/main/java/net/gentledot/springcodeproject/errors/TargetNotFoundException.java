package net.gentledot.springcodeproject.errors;

public class TargetNotFoundException extends RuntimeException {
    private TargetNotFoundException(String message) {
        super(message);
    }

    public TargetNotFoundException(Long id, Class targetClass) {
        this(String.format("대상을 찾을 수 없습니다. (ID : %s, Domain : %s)", id, targetClass.getName()));
    }
}
