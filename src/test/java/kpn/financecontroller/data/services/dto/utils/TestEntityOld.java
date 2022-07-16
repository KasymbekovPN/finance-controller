package kpn.financecontroller.data.services.dto.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// TODO: 16.07.2022 del
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "model")
public class TestEntityOld {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String value;

    public TestEntityOld(TestModelOld model) {
        id = model.getId();
        value = model.getValue();
    }
}
