package kpn.financecontroller.initialization.generators.valued;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Properties implements Valued<String> {
    FILE_READING_RESULT("fileReading.result"),
    JSON_OBJECT_CREATION_RESULT("json.object.creation.result");

    @Getter
    private final String value;
}
