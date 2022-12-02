package dev.antoniomayk.todotopia.api.common.entity;

import dev.antoniomayk.todotopia.api.common.dto.UserDTO;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue
    private UUID uuid;

    @Column(unique = true, nullable = false)
    private String email;

    @With
    @Column(nullable = false)
    private String password;

    private String firstName;

    private String lastName;

    public UserDTO toDTO() {
        return new ModelMapper().map(this, UserDTO.class);
    }

}
