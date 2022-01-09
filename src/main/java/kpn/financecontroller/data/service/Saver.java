package kpn.financecontroller.data.service;

import kpn.financecontroller.result.Result;

public interface Saver<D, E> {
    Result<D> save(E entity);
}
