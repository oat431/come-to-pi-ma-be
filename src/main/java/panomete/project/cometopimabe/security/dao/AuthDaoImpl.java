package panomete.project.cometopimabe.security.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import panomete.project.cometopimabe.security.entity.Authorities;
import panomete.project.cometopimabe.security.entity.Location;
import panomete.project.cometopimabe.security.entity.Roles;
import panomete.project.cometopimabe.security.entity.Users;
import panomete.project.cometopimabe.security.repository.AuthRepository;
import panomete.project.cometopimabe.security.repository.AuthoritiesRepository;
import panomete.project.cometopimabe.security.repository.LocationRepository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AuthDaoImpl implements AuthDao {
    final AuthRepository authRepository;
    final AuthoritiesRepository authoritiesRepository;
    final LocationRepository locationRepository;
    @Override
    public Users getUserByUsername(String username) {
        return authRepository.findByUsername(username);
    }

    @Override
    public Users getUserByEmail(String email) {
        return authRepository.findByEmail(email);
    }

    @Override
    public Users getUserById(UUID id) {
        return authRepository.findById(id);
    }

    @Override
    public Users saveUser(Users user) {
        return authRepository.save(user);
    }

    @Override
    public Users deleteUser(Users user) {
        authRepository.delete(user);
        return user;
    }

    @Override
    public Authorities saveAuthority(Authorities authority) {
        return authoritiesRepository.save(authority);
    }

    @Override
    public Authorities getAuthorityByName(Roles name) {
        return authoritiesRepository.findByName(name);
    }

    @Override
    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public Page<Users> getAllLockedUser(PageRequest pageRequest) {
        return authRepository.findAllByEnablesFalse(pageRequest);
    }

    @Override
    public Page<Users> getAllUsers(PageRequest pageRequest) {
        return authRepository.findAll(pageRequest);
    }
}
