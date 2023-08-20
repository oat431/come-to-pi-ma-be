package panomete.project.cometopimabe.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import panomete.project.cometopimabe.security.dao.AuthDao;
import panomete.project.cometopimabe.security.entity.Authorities;
import panomete.project.cometopimabe.security.entity.Location;
import panomete.project.cometopimabe.security.entity.Roles;
import panomete.project.cometopimabe.security.entity.Users;
import panomete.project.cometopimabe.security.payload.request.RegisterRequest;
import panomete.project.cometopimabe.security.payload.request.UpdateRequest;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    final AuthDao authDao;
    private final PasswordEncoder encoder = new BCryptPasswordEncoder();
    @Override
    public Users getUserByUsername(String username) {
        return authDao.getUserByUsername(username);
    }

    @Override
    public Users getUserByEmail(String email) {
        return authDao.getUserByEmail(email);
    }

    @Override
    public Users getUserById(UUID id) {
        return authDao.getUserById(id);
    }

    @Override
    public Users createUser(RegisterRequest user) {
        Authorities role = authDao.getAuthorityByName(Roles.ROLE_USER);

        Location location = Location.builder()
                .address(user.address())
                .city(user.city())
                .state(user.state())
                .country(user.country())
                .zip(user.zip())
                .build();
        authDao.saveLocation(location);

        Users newUser = Users.builder()
                .profilePicture(user.profilePicture())
                .username(user.username())
                .platformName(user.platformName())
                .password(encoder.encode(user.password()))
                .email(user.email())
                .firstName(user.firstName())
                .lastName(user.lastName())
                .tel(user.tel())
                .birthday(user.birthday())
                .location(location)
                .authorities(List.of(role))
                .build();
        return authDao.saveUser(newUser);
    }

    @Override
    public Users updateUser(UUID userId, UpdateRequest user) {
        Users oldUser = authDao.getUserById(userId);
        Location location = oldUser.getLocation();
        location.setAddress(user.address());
        location.setCity(user.city());
        location.setState(user.state());
        location.setCountry(user.country());
        location.setZip(user.zip());
        authDao.saveLocation(location);

        oldUser.setProfilePicture(user.profilePicture());
        oldUser.setPlatformName(user.platformName());
        oldUser.setFirstName(user.firstName());
        oldUser.setLastName(user.lastName());
        oldUser.setTel(user.tel());
        oldUser.setBirthday(user.birthday());
        oldUser.setLocation(location);

        return authDao.saveUser(oldUser);
    }
}
