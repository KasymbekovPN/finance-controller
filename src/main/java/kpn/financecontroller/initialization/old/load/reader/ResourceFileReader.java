package kpn.financecontroller.initialization.old.load.reader;

import kpn.financecontroller.result.Result;

// TODO: 27.02.2022 del ???
public interface ResourceFileReader {
    Result<String> read(String path);
}
