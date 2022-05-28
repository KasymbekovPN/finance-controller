package kpn.financecontroller.data.services.dto.loaders;

import kpn.lib.result.Result;

import java.util.List;

// TODO: 28.05.2022 it must be BaseLoader
public interface Loader<D, E, I>{
    Result<D> byId(I id);
    Result<List<D>> by(String attribute, Object value);
    Result<List<D>> all();
}
