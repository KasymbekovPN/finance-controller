package kpn.financecontroller.data.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kpn.financecontroller.data.domains.user.Role;
import kpn.lib.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@Entity(name = "usr")
public class UserEntity extends AbstractEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @NotEmpty
    private String username;
    @JsonIgnore
    private String password;
    private Role role;
}
