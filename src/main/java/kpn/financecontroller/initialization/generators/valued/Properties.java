package kpn.financecontroller.initialization.generators.valued;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Properties implements Valued<String> {
    FILE_CONTENT("file.content");

    @Getter
    private final String value;
}
