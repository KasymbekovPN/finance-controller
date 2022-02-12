package kpn.financecontroller.initialization.save.managers;

import kpn.financecontroller.checkers.GroupChecker;
import kpn.financecontroller.initialization.collectors.LoadDataCollector;
import kpn.financecontroller.result.Result;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
abstract public class AbstractSaveManager<K, E> implements SaveManager<K, E>{
    protected final String ID;
    protected final GroupChecker<LoadDataCollector<?, ?>> collectorChecker;

    @Override
    public Result<Void> clearTarget() {
        Result<Void> result = checkCollectors();
        return result.getSuccess() ? deleteBefore() : result;
    }

    @Override
    public Result<Void> save() {
        Result<Void> result = checkCollectors();
        return result.getSuccess() ? saveImpl() : result;
    }

    protected abstract Result<Void> checkCollectors();
    protected abstract Result<Void> deleteBefore();
    protected abstract Result<Void> saveImpl();
}