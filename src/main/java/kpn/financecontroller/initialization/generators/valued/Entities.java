package kpn.financecontroller.initialization.generators.valued;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Entities implements Valued<String> {
    TAGS("tags"),
    COUNTRIES("countries"),
    REGIONS("regions"),
    CITIES("cities"),
    STREETS("streets"),
    ADDRESSES("addresses"),
    PLACES("places");

    @Getter
    private final String value;
}
