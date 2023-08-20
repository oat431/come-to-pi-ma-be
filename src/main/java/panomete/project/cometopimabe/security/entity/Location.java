package panomete.project.cometopimabe.security.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Location implements Serializable {
    @Serial
    private static final long serialVersionUID = 2L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String address;
    String city;
    String state;

    @Builder.Default
    String country = "Thailand";

    String zip;

    @OneToOne(mappedBy = "location", cascade = CascadeType.ALL)
    Users user;
}
