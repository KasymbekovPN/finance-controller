package kpn.financecontroller.initialization.generators.valued;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Codes implements Valued<String> {
    FAIL_FILE_READING("file.notExist");

    @Getter
    private final String value;
}
