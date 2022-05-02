package kpn.financecontroller.data.services.dto.savers;

import kpn.lib.result.Result;

public interface Saver<D, E, I> {
    Result<D> save(E entity);
}
