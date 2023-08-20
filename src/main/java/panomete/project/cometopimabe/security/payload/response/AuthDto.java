package panomete.project.cometopimabe.security.payload.response;

public record AuthDto (
    String id,
    String profilePicture,
    String platformName,
    String username,
    String name,
    String tel,
    Integer age,
    String email,
    LocationDto location,
    String role
) { }
