package kpn.financecontroller.data.services.dto;

import kpn.financecontroller.data.services.dto.deleters.Deleter;
import kpn.financecontroller.data.services.dto.loaders.Loader;
import kpn.financecontroller.data.services.dto.savers.Saver;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DTOServiceImpl<D, E, I> implements DTOService<D, E, I> {
    private final Saver<D, E, I> saver;
    private final Loader<D, E, I> loader;
    private final Deleter<D, E, I> deleter;

    @Override
    public Saver<D, E, I> saver() {
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
