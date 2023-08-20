package panomete.project.cometopimabe.security.payload.response;

import java.util.List;

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
    List<String> roles
) { }
