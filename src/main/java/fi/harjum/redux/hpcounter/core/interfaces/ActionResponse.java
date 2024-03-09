package fi.harjum.redux.hpcounter.core.interfaces;

public record ActionResponse(String message, String error, Object payload, int code) {
}
