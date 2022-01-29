package kpn.financecontroller.data.services.savers;

import kpn.financecontroller.data.services.DTOServiceException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.function.Function;

public class SaverImpl<D, E, I> extends AbstractSaver<D, E, I>{

    private final JpaRepository<E, I> repo;
    private final Function<E, D> toDomain;

    public SaverImpl(JpaRepository<E, I> repo, Function<E, D> toDomain, String name) {
        super(name);
        this.repo = repo;
        this.toDomain = toDomain;
    }

    @Override
    protected E saveImpl(E entity) throws DTOServiceException {
        try{
            return repo.save(entity);
        } catch (Throwable t){
            throw new DTOServiceException("saver.saveImpl.fail");
        }
    }

    @Override
    protected D convertEntityToDomain(E entity) {
        return toDomain.apply(entity);
    }
}
