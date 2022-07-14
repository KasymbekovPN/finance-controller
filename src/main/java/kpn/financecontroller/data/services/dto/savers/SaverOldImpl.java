package kpn.financecontroller.data.services.dto.savers;

import kpn.financecontroller.data.services.dto.DTOServiceExceptionOld;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.function.Function;

// TODO: 14.07.2022 del
public class SaverOldImpl<D, E, I> extends AbstractSaverOld<D, E, I> {

    private final JpaRepository<E, I> repo;
    private final Function<E, D> toDomain;

    public SaverOldImpl(JpaRepository<E, I> repo, Function<E, D> toDomain, String name) {
        super(name);
        this.repo = repo;
        this.toDomain = toDomain;
    }

    @Override
    protected E saveImpl(E entity) throws DTOServiceExceptionOld {
        try{
            return repo.save(entity);
        } catch (Throwable t){
            throw new DTOServiceExceptionOld("saver.saveImpl.fail");
        }
    }

    @Override
    protected D convertEntityToDomain(E entity) {
        return toDomain.apply(entity);
    }
}
