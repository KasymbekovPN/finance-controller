package kpn.financecontroller.data.loaders;

import kpn.financecontroller.result.Result;

import java.util.List;

public interface Loader<D, E, I>{
    Result<D> byId(I id);
    Result<List<D>> by(String attribute, Object value);
    Result<List<D>> all();
}
