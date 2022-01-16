package kpn.financecontroller.data.services.savers;

import kpn.financecontroller.result.Result;

public interface Saver<D, E, I> {
    Result<D> save(E entity);
}
