package panomete.project.cometopimabe.security.payload.request;

import java.util.Date;

public record RegisterRequest (
    String profilePicture,
    String platformName,
    String username,
    String firstName,
    String lastName,
    String tel,
    String password,
    Date birthday,
    String email,
    String address,
    String city,
    String state,
    String country,
    String zip
) { }
