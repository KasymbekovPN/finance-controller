package kpn.financecontroller.data.propertyExtractors.fillers;

import kpn.financecontroller.data.propertyExtractors.IECollector;
import kpn.financecontroller.result.Result;

// TODO: 05.02.2022 del
public interface CollectorFiller<K, E> {
    Result<Void> fill(String jsonContent);
    IECollector<K, E> get();
}
