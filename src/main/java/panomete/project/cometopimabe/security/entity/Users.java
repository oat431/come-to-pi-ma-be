package panomete.project.cometopimabe.security.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users implements UserDetails, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    String profilePicture;
    String platformName;

    @Column(unique = true)
    String username;

    String firstName;
    String lastName;

    @Column(unique = true)
    String tel;

    String password;
    Date birthday;

    @Builder.Default
    Date lastPasswordReset = Date.from(LocalDate.of(2023, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant());

    @Builder.Default
    Boolean enables = false;

    @Email
    @Column(unique = true)
    String email;

    @OneToOne
    Location location;

    @ManyToMany(fetch = FetchType.EAGER)
    List<Authorities> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities
                .stream()
                .map(authorities -> new SimpleGrantedAuthority(authorities.getName().name()))
                .toList();
    }

    public List<Authorities> getSimpleAuthorities() {
        return this.authorities;
    }

    public Integer getAge() {
        return LocalDate.now().getYear() - birthday.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear();
    }

    public String getUserId() {
        return id.toString();
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enables;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enables;
    }

}
