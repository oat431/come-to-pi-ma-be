package panomete.project.cometopimabe.security.service;


import panomete.project.cometopimabe.security.entity.Users;
import panomete.project.cometopimabe.security.payload.request.RegisterRequest;
import panomete.project.cometopimabe.security.payload.request.UpdateRequest;

import java.util.UUID;

public interface AuthService {
    Users getUserByUsername(String username);
    Users getUserByEmail(String email);
    Users getUserById(UUID id);
    Users createUser(RegisterRequest user);
    Users updateUser(UUID userId, UpdateRequest user);
}
