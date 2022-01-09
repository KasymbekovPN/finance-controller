package kpn.financecontroller.data.service;

import kpn.financecontroller.result.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class AbstractDTOService<D,E> {

    private static final String FAIL_SAVING_TEMPLATE = "service.%s.save.fail";
    private static final String NO_ONE_BY_ID = "service.%s.loadById.noOne";

    protected Result<D> saveImpl(E entity, JpaRepository<E, Long> repo){
        try{
            repo.save(entity);
            return getResultBuilder()
                    .success(true)
                    .value(convertEntityToDomain(entity))
                    .build();
        } catch (Throwable t){
           // TODO: 05.01.2022 extend definition
            return getResultBuilder()
                    .success(false)
                    .code(getSaveFailCode())
                    .build();
        }
    }

    protected Result<List<D>> loadAllImpl(JpaRepository<E, Long> repo){
        List<E> entities = repo.findAll();
        return getListResultBuilder()
                .success(true)
                .value(convertEntitiesToDomains(entities))
                .build();
    }

    protected void deleteAllImpl(JpaRepository<E, Long> repo) {
        repo.deleteAll();
    }

    protected Result<D> loadByIdImpl(Long id, JpaRepository<E, Long> repo){
        Optional<E> maybeEntity = repo.findById(id);
        if (maybeEntity.isPresent()){
            return getResultBuilder()
                    .success(true)
                    .value(convertEntityToDomain(maybeEntity.get()))
                    .build();
        }
        return getResultBuilder()
                .success(false)
                .code(getNoOneByIdCode())
                .arg(id)
                .build();
    }

    protected void deleteByIdImpl(Long id, JpaRepository<E, Long> repo) {
        repo.deleteById(id);
    }

    public Result<List<D>> searchImpl(String filter, JpaRepository<E, Long> repo) {
        if (filter == null || filter.isEmpty()){
            return loadAllImpl(repo);
        }

        List<E> entities = getSearchedEntities(filter);
        return getListResultBuilder()
                .success(true)
                .value(convertEntitiesToDomains(entities))
                .build();
    }

    protected String getSaveFailCode() {
        return String.format(FAIL_SAVING_TEMPLATE, getServiceId());
    }

    private String getNoOneByIdCode() {
        return String.format(NO_ONE_BY_ID, getServiceId());
    }

    protected String getServiceId(){
        return getClass().getSimpleName();
    }

    protected abstract Result.Builder<D> getResultBuilder();
    protected abstract Result.Builder<List<D>> getListResultBuilder();
    protected abstract D convertEntityToDomain(E entity);
    protected abstract List<D> convertEntitiesToDomains(List<E> entities);
    protected abstract List<E> getSearchedEntities(String filter);
}
