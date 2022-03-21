package kpn.financecontroller.initialization.generators.valued;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Properties implements Valued<String> {
    FILE_READING_RESULT("fileReading.result"),
    JSON_OBJECT_CREATION_RESULT("json.object.creation.result"),
    JSON_TO_DB_CONVERSION_RESULT("json2db.conversion.result");

    @Getter
    private final String value;
}
