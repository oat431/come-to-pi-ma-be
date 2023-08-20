package panomete.project.cometopimabe.security.payload.request;

public record LoginRequest (
    String username,
    String email,
    String password
) { }
