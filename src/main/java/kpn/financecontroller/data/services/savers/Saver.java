package kpn.financecontroller.data.services.savers;

import kpn.lib.result.Result;

public interface Saver<D, E, I> {
    Result<D> save(E entity);
}
