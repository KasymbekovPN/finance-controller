package kpn.financecontroller.data.services;

import kpn.financecontroller.data.services.deleters.Deleter;
import kpn.financecontroller.data.services.loaders.Loader;
import kpn.financecontroller.data.services.savers.Saver;

public interface DTOService<D, E, I> {
    Saver<D, E> saver();
    Loader<D, E, I> loader();
    Deleter<D, E, I> deleter();
}
