package kpn.financecontroller.data.propertyExtractors;

import kpn.financecontroller.result.Result;

public interface ResourceFileReader {
    Result<String> read(String path);
}
