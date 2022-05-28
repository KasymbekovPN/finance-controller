package kpn.financecontroller.data.services.dto.deleters;

import kpn.lib.result.Result;

// TODO: 28.05.2022 it must be BaseDeleter
public interface Deleter<D, E, I>{
    Result<Void> byId(I id);
    Result<Void> by(String attribute, Object value);
    Result<Void> all();
}
