package kpn.financecontroller.data.services.statistic.byTag;

import kpn.financecontroller.data.services.statistic.byTag.query.Query;
import kpn.lib.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
final public class ByTagStatisticServiceImpl implements ByTagStatisticService<Query> {

    private final Function<Query, Result<Void>> checker;

    @Autowired
    public ByTagStatisticServiceImpl(Function<Query, Result<Void>> checker) {
        this.checker = checker;
    }

    /*

            @Query("select c from Contact c " +
            "where lower(c.firstName) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(c.lastName) like lower(concat('%', :searchTerm, '%'))")
    List<Contact> search(@Param("searchTerm") String searchTerm);

         */

    @Override
    public String calculate(Query query) {
        return null;
    }
}
