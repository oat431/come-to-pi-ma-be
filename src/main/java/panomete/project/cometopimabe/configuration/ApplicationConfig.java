package panomete.project.cometopimabe.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import panomete.project.cometopimabe.security.entity.Authorities;
import panomete.project.cometopimabe.security.entity.Roles;
import panomete.project.cometopimabe.security.repository.AuthoritiesRepository;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationConfig implements ApplicationListener<ApplicationReadyEvent> {
    final AuthoritiesRepository authoritiesRepository;
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("--adding role--");
        authoritiesRepository.saveAll(
                List.of(
                        Authorities.builder().name(Roles.ROLE_USER).build(),
        Authorities.builder().name(Roles.ROLE_ADMIN).build(),
        Authorities.builder().name(Roles.ROLE_MENTOR).build(),
        Authorities.builder().name(Roles.ROLE_EMPLOYEE).build()
                )
        );
        log.info("=====Application Started======");
    }
}
