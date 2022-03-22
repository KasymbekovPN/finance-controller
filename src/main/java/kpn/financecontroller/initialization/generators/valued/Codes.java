package kpn.financecontroller.initialization.generators.valued;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Codes implements Valued<String> {
    FAIL_FILE_READING("file.notExist"),
    NO_STRING_CONTENT("no.string.content"),
    JSON_SYNTAX_EXCEPTION("json.syntax.exception"),
    NO_JSON_OBJECT("no.json.object"),
    DB_FAIL_CLEANING("fail.cleaning"),
    CONVERSION_RESULT_NOT_EXIST_ON_SAVING("conversion.result.not.exist.on.saving"),
    ENTITY_NOT_EXIST_ON_SAVING("entity.not.exist.on.saving"),
    FAIL_SAVING_ATTEMPT("fail.saving.attempt");

    @Getter
    private final String value;
}
