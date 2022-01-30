package kpn.financecontroller.data.initialEntities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

// TODO: 30.01.2022 del
@NoArgsConstructor
@Setter
@Getter
public class Collector<T> {
    private Boolean deleteBefore;
    private List<T> entities;
}
