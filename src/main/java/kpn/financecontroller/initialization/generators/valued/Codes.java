package kpn.financecontroller.initialization.generators.valued;

import kpn.financecontroller.initialization.generators.valued.Valued;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Codes implements Valued<String> {
    FILE_NOT_EXIST("file.notExist");

    @Getter
    private final String value;
}
