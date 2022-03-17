package kpn.financecontroller.initialization.generators.valued;

import kpn.financecontroller.initialization.generators.valued.Valued;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Entities implements Valued<String> {
    TAGS("tags");

    @Getter
    private final String value;
}
