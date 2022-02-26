package kpn.financecontroller.data.services.loaders;

import kpn.financecontroller.data.services.DTOServiceException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

final public class LoaderAllAndById<D, E, I> extends AbstractLoader<D, E, I> {
    private final JpaRepository<E, I> repo;
    private final Function<E, D> toDomain;
    private final Function<List<E>, List<D>> toDomains;

    public LoaderAllAndById(JpaRepository<E, I> repo, String name, Function<E, D> toDomain, Function<List<E>, List<D>> toDomains) {
        super(name);
        this.repo = repo;
        this.toDomain = toDomain;
        this.toDomains = toDomains;
    }

    @Override
    protected List<E> loadAll() throws DTOServiceException {
        try{
            return repo.findAll();
        } catch (Throwable t){
            throw new DTOServiceException("loader.loadAll.fail");
        }
    }

    @Override
    protected Optional<E> loadById(I id) throws DTOServiceException {
        try{
            return repo.findById(id);
        } catch (Throwable t){
            throw new DTOServiceException("loader.loadById.fail");
        }
    }

    @Override
    protected D convertEntityToDomain(E entity) {
        return toDomain.apply(entity);
    }

    @Override
    protected List<D> convertEntitiesToDomains(List<E> entities) {
        return toDomains.apply(entities);
    }
}