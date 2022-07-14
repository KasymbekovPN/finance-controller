package kpn.financecontroller.data.services.dto.savers;

import kpn.lib.result.Result;

// TODO: 14.07.2022 del
// TODO: 28.05.2022 it must be BaseSaver
public interface SaverOld<D, E, I> {
    Result<D> save(E entity);
}
