package kpn.financecontroller.data.service;

import kpn.financecontroller.result.Result;

public interface Deleter<D, E, I>{
    Result<Void> byId(I id);
    Result<Void> by(String attribute, Object value);
    Result<Void> all();
}
