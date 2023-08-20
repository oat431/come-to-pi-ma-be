package panomete.project.cometopimabe.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import panomete.project.cometopimabe.security.entity.Authorities;
import panomete.project.cometopimabe.security.entity.Roles;

public interface AuthoritiesRepository extends JpaRepository<Authorities, Long> {
    Authorities findByName(Roles name);
}
