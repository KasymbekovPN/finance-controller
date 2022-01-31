package kpn.financecontroller.data.propertyExtractors;

import kpn.financecontroller.result.Result;

public interface SpecificPropertyExtractor<T> {
    Result<T> extract();
}
