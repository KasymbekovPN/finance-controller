package kpn.financecontroller.initialization.load.reader;

import kpn.financecontroller.result.Result;

public interface ResourceFileReader {
    Result<String> read(String path);
}
