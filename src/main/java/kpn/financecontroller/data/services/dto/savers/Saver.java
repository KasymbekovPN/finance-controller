package kpn.financecontroller.data.services.dto.savers;

import kpn.lib.result.Result;

// TODO: 28.05.2022 it must be BaseSaver
public interface Saver<D, E, I> {
    Result<D> save(E entity);
}
