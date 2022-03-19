package kpn.financecontroller.initialization.tasks.testUtils;

import kpn.financecontroller.initialization.generators.valued.Valued;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TestKeys implements Valued<String> {
    KEY("key");

    @Getter
    private final String value;
}
