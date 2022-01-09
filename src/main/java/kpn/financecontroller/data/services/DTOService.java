package kpn.financecontroller.data.services;

import kpn.financecontroller.data.deleters.Deleter;
import kpn.financecontroller.data.loaders.Loader;
import kpn.financecontroller.data.savers.Saver;

public interface DTOService<D, E, I> {
    Saver<D, E> saver();
    Loader<D, E, I> loader();
    Deleter<D, E, I> deleter();
}
