package kpn.financecontroller.data.propertyExtractors;

import kpn.financecontroller.result.Result;

public interface PropertyExtractor<V>{
    Result<V> extract();
}
