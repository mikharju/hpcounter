package fi.harjum.redux.hpcounter.core.interfaces;

public class HpCounterException extends RuntimeException {
    public HpCounterException(String message, Object ... params) {
        super(String.format(message, params));
    }

}
