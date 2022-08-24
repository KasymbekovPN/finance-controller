package kpn.financecontroller.gui.external.builder;

import com.querydsl.core.types.Predicate;

public interface QBuilder {
    Predicate build();
    QBuilder or();
    QBuilder and();
    QBuilder getParent();
}
