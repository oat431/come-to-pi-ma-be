package panomete.project.cometopimabe.security.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import panomete.project.cometopimabe.security.entity.Authorities;
import panomete.project.cometopimabe.security.entity.Location;
import panomete.project.cometopimabe.security.entity.Roles;
import panomete.project.cometopimabe.security.entity.Users;

import java.util.UUID;

public interface AuthDao {
    Users getUserByUsername(String username);
    Users getUserByEmail(String email);
    Users getUserById(UUID id);
    Users saveUser(Users user);

    Users deleteUser(Users user);

    Authorities saveAuthority(Authorities authority);
    Authorities getAuthorityByName(Roles name);

    Location saveLocation(Location location);

    Page<Users> getAllLockedUser(PageRequest pageRequest);

    Page<Users> getAllUsers(PageRequest pageRequest);
}
