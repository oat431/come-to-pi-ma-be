package panomete.project.cometopimabe.security.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import panomete.project.cometopimabe.security.entity.Users;

import java.util.UUID;

public interface AuthRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);
    Users findByEmail(String email);
    Users findById(UUID id);

    Page<Users> findAllByEnablesFalse(Pageable pageable);
}
