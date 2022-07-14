package kpn.financecontroller.data.services.dto.deleters;

import kpn.financecontroller.data.services.dto.DTOServiceExceptionOld;
import org.springframework.data.jpa.repository.JpaRepository;

// TODO: 14.07.2022 del
final public class DeleterOldAllAndById<D, E, I> extends AbstractDeleterOld<D, E, I> {

    private final JpaRepository<E, I> repo;

    public DeleterOldAllAndById(JpaRepository<E, I> repo, String name) {
        super(name);
        this.repo = repo;
    }

    @Override
    protected void deleteById(I id) throws DTOServiceExceptionOld {
        try{
            repo.deleteById(id);
        } catch (Throwable t){
            throw new DTOServiceExceptionOld("deleter.deleteById.fail");
        }
    }

    @Override
    protected void deleteAll() throws DTOServiceExceptionOld {
        try{
            repo.deleteAll();
        } catch (Throwable t){
            throw new DTOServiceExceptionOld("deleter.deleteAll.fail");
        }
    }
}
