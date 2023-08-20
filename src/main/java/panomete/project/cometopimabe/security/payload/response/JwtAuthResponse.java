package panomete.project.cometopimabe.security.payload.response;

public record JwtAuthResponse (
    String sub,
    String role,
    String created,
    String id,
    Long exp
) { }