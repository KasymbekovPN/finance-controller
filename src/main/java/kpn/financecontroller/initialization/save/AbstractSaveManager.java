package kpn.financecontroller.initialization.save;

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
        if (result.getSuccess()){
            return checkNeedDeleteBefore()
                    ? deleteBefore()
                    : Result.<Void>builder()
                        .success(true)
                        .code("saveManager.deleteBefore.disabled")
                        .arg(ID)
                        .build();
        }
        return result;
    }

    @Override
    public Result<Void> save() {
        Result<Void> result = checkCollectors();
        return result.getSuccess() ? saveImpl() : result;
    }

    protected abstract Result<Void> checkCollectors();
    protected abstract boolean checkNeedDeleteBefore();
    protected abstract Result<Void> deleteBefore();
    protected abstract Result<Void> saveImpl();
}
