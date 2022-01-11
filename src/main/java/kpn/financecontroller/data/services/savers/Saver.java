package kpn.financecontroller.data.services.savers;

import kpn.financecontroller.result.Result;

public interface Saver<D, E> {
    Result<D> save(E entity);
}
