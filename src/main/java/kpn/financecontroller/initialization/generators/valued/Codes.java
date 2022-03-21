package kpn.financecontroller.initialization.generators.valued;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Codes implements Valued<String> {
    FAIL_FILE_READING("file.notExist"),
    NO_STRING_CONTENT("no.string.content"),
    JSON_SYNTAX_EXCEPTION("json.syntax.exception"),
    NO_JSON_OBJECT("no.json.object");

    @Getter
    private final String value;
}
