package panomete.project.cometopimabe.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import panomete.project.cometopimabe.security.entity.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
