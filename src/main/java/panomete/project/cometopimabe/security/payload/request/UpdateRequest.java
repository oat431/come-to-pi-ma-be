package panomete.project.cometopimabe.security.payload.request;

import java.util.Date;

public record UpdateRequest (
    String profilePicture,
    String platformName,
    String firstName,
    String lastName,
    String tel,
    Date birthday,
    String address,
    String city,
    String state,
    String country,
    String zip
) { }
