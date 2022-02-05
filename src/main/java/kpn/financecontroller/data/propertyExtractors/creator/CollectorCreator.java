package kpn.financecontroller.data.propertyExtractors.creator;

import kpn.financecontroller.data.propertyExtractors.IECollector;
import kpn.financecontroller.result.Result;

public interface CollectorCreator<K, E> {
    Result<IECollector<K, E>> create(String source);
    Result<IECollector<K, E>> get();
}
