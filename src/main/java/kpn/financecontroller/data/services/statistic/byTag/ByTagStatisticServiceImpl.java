package kpn.financecontroller.data.services.statistic.byTag;

import kpn.financecontroller.data.services.statistic.byTag.query.Query;
import kpn.lib.result.Result;
import kpn.lib.seed.Seed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
final public class ByTagStatisticServiceImpl implements ByTagStatisticService<Query, Seed> {

    private final Function<Query, Result<Void>> checker;

    @Autowired
    public ByTagStatisticServiceImpl(Function<Query, Result<Void>> checker) {
        this.checker = checker;
    }

    @Override
    public Seed calculate(Query query) {
        Result<Void> checkingResult = checker.apply(query);
        if (checkingResult.isSuccess()){
            return null;
        }
        return checkingResult.getSeed();
    }
}
