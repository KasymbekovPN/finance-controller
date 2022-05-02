package kpn.financecontroller.data.services.dto;

import kpn.financecontroller.data.services.dto.deleters.Deleter;
import kpn.financecontroller.data.services.dto.loaders.Loader;
import kpn.financecontroller.data.services.dto.savers.Saver;

public interface DTOService<D, E, I> {
    Saver<D, E, I> saver();
    Loader<D, E, I> loader();
    Deleter<D, E, I> deleter();
}
