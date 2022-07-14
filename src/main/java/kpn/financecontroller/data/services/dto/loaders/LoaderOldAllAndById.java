package kpn.financecontroller.data.services.dto.loaders;

import kpn.financecontroller.data.services.dto.DTOServiceExceptionOld;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

// TODO: 14.07.2022 del
final public class LoaderOldAllAndById<D, E, I> extends AbstractLoaderOld<D, E, I> {
    private final JpaRepository<E, I> repo;
    private final Function<E, D> toDomain;
    private final Function<List<E>, List<D>> toDomains;

    public LoaderOldAllAndById(JpaRepository<E, I> repo, String name, Function<E, D> toDomain, Function<List<E>, List<D>> toDomains) {
        super(name);
        this.repo = repo;
        this.toDomain = toDomain;
        this.toDomains = toDomains;
    }

    @Override
    protected List<E> loadAll() throws DTOServiceExceptionOld {
        try{
            return repo.findAll();
        } catch (Throwable t){
            throw new DTOServiceExceptionOld("loader.loadAll.fail");
        }
    }

    @Override
    protected Optional<E> loadById(I id) throws DTOServiceExceptionOld {
        try{
            return repo.findById(id);
        } catch (Throwable t){
            throw new DTOServiceExceptionOld("loader.loadById.fail");
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
