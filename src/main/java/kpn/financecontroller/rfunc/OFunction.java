package kpn.financecontroller.rfunc;

import java.util.Optional;

@FunctionalInterface
public interface OFunction<TYPE, RESULT> {
    Optional<RESULT> apply(TYPE value);
}
