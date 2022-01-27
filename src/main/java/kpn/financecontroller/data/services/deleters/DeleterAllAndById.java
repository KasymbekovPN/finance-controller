package kpn.financecontroller.data.services.deleters;

import kpn.financecontroller.data.services.DTOServiceException;
import org.springframework.data.jpa.repository.JpaRepository;

final public class DeleterAllAndById<D, E, I> extends AbstractDeleter<D, E, I>{

    private final JpaRepository<E, I> repo;

    public DeleterAllAndById(JpaRepository<E, I> repo, String name) {
        super(name);
        this.repo = repo;
    }

    @Override
    protected void deleteById(I id) throws DTOServiceException {
        try{
            repo.deleteById(id);
        } catch (Throwable t){
            throw new DTOServiceException("deleter.deleteById.fail");
        }
    }

    @Override
    protected void deleteAll() throws DTOServiceException {
        try{
            repo.deleteAll();
        } catch (Throwable t){
            throw new DTOServiceException("deleter.deleteAll.fail");
        }
    }
}
