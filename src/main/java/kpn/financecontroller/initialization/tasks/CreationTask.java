package kpn.financecontroller.initialization.tasks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import kpn.financecontroller.initialization.generators.valued.Codes;
import kpn.financecontroller.initialization.generators.valued.Properties;
import kpn.financecontroller.initialization.listeners.jobjects.LongKeyJsonObj;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.financecontroller.initialization.storage.ObjectStorage;
import kpn.lib.result.Result;
import kpn.taskexecutor.lib.contexts.Context;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.time.LocalDate;

final public class CreationTask extends BaseTask {
    @Setter
    private ObjectStorageCreator objectStorageCreator;

    @Override
    public void execute(Context context) {
        reset();
        ObjectStorage storage = null;
        ResultContextManager contextManager = createContextManager(context);
        Result<String> fileReadingResult = contextManager.get(key, Properties.FILE_READING_RESULT, String.class);
        if (fileReadingResult.isSuccess()){
            try{
                storage = objectStorageCreator.create(fileReadingResult.getValue());
                continuationPossible = true;
            } catch (JsonSyntaxException ex){
                calculateAndSetCode(key, Codes.JSON_SYNTAX_EXCEPTION);
            }
        } else {
            calculateAndSetCode(key, Codes.NO_STRING_CONTENT);
        }

        putResultIntoContext(context, Properties.JSON_OBJECT_CREATION_RESULT, storage);
    }

    @RequiredArgsConstructor
    public static class ObjectStorageCreator {
        private final Class<? extends LongKeyJsonObj<?>> type;

        public ObjectStorage create(String source){
            LongKeyJsonObj<?> jsonObj = createGson().fromJson(source, type);
            ObjectStorage storage = new ObjectStorage();
            storage.putAll(jsonObj.getEntities());
            return storage;
        }

        private Gson createGson() {
            return new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
        }
    }

    public static class LocalDateAdapter extends TypeAdapter<LocalDate> {
        @Override
        public void write(JsonWriter out, LocalDate value) throws IOException {
            if (value == null){
                out.nullValue();
            } else {
                out.value(value.toString());
            }
        }

        @Override
        public LocalDate read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL){
                in.nextNull();
                return null;
            }
            return LocalDate.parse(in.nextString());
        }
    }
}