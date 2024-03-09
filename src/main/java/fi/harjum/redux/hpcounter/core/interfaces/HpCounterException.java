package fi.harjum.redux.hpcounter.core.interfaces;

public class HpCounterException extends RuntimeException {
    private final int errorCode;
    public HpCounterException(String message) {
        super(message);
        errorCode = 1;
    }

    public HpCounterException(int code, String message) {
        super(message);
        errorCode = code;
    }

    public HpCounterException(int code, String message, Object ... params) {
        super(String.format(message, params));
        errorCode = 1;
    }

}
