package kpn.financecontroller.data.services.dto.loaders;

import kpn.lib.result.Result;

import java.util.List;

// TODO: 14.07.2022 del
// TODO: 28.05.2022 it must be BaseLoader
public interface LoaderOld<D, E, I>{
    Result<D> byId(I id);
    Result<List<D>> by(String attribute, Object value);
    Result<List<D>> all();
}
