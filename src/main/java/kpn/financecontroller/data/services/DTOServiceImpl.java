package kpn.financecontroller.data.services;

import kpn.financecontroller.data.services.deleters.Deleter;
import kpn.financecontroller.data.services.loaders.Loader;
import kpn.financecontroller.data.services.savers.Saver;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DTOServiceImpl<D, E, I> implements DTOService<D, E, I> {
    private final Saver<D, E> saver;
    private final Loader<D, E, I> loader;
    private final Deleter<D, E, I> deleter;

    @Override
    public Saver<D, E> saver() {
        return saver;
    }

    @Override
    public Loader<D, E, I> loader() {
        return loader;
    }

    @Override
    public Deleter<D, E, I> deleter() {
        return deleter;
    }
}
