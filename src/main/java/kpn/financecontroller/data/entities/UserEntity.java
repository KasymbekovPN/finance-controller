package kpn.financecontroller.data.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kpn.financecontroller.data.domain.user.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@Entity(name = "usr")
public class UserEntity extends AbstractEntity {
    @NotEmpty
    private String username;
    @JsonIgnore
    private String password;
    private Role role;
}
