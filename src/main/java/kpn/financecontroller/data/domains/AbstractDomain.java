package kpn.financecontroller.data.domains;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Setter
@Getter
public abstract class AbstractDomain {
    protected Long id;
}
