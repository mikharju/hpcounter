package fi.harjum.redux.hpcounter.core.interfaces;

public class ResponseFactory {

    public static ActionResponse success(String msg, Object ... params) {
        return new ActionResponse(String.format(msg, params), null, null, 1);
    }

    public static ActionResponse payload(Object payload, String msg, Object ... params) {
        return new ActionResponse(String.format(msg, params), null, payload, 1);
    }

    public static ActionResponse error(int code, String errorMsg, Object ... params) {
        return new ActionResponse(null, String.format(errorMsg, params), null, code);
    }
}
