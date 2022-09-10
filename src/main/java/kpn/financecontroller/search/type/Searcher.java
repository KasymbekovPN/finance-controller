package kpn.financecontroller.search.type;

import java.util.Set;

public interface Searcher<T, R> {
    R search(T arg);
}
