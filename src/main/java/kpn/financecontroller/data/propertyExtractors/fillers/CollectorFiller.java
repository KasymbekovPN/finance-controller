package kpn.financecontroller.data.propertyExtractors.fillers;

import kpn.financecontroller.data.propertyExtractors.IECollector;
import kpn.financecontroller.result.Result;

public interface CollectorFiller<K, E> {
    Result<Void> fill(String jsonContent);
    IECollector<K, E> get();
}
