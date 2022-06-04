package kpn.financecontroller.rfunc;

import java.util.Optional;

// TODO: 04.06.2022 del 
@FunctionalInterface
public interface OFunction<TYPE, RESULT> {
    Optional<RESULT> apply(TYPE value);
}
