// TODO: 16.03.2022 del
//package kpn.financecontroller.initialization._task.task;
//
//import kpn.financecontroller.initialization._context.context.Context;
//import kpn.financecontroller.result.Result;
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.Optional;
//
//@RequiredArgsConstructor
//public class FileReadingTask implements Task {
//
//    @Getter
//    private final String key;
//    @Getter
//    private boolean continuationPossible;
//
//    @Override
//    public void execute(Context context){
//        continuationPossible = false;
//        Result.Builder<String> builder = Result.<String>builder().arg(key);
//
//        Optional<Object> maybePath = context.get(key, Properties.PATH.getValue());
//        if (maybePath.isPresent()){
//            String path = String.valueOf(maybePath.get());
//            try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path)){
//                if (inputStream != null){
//                    try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))){
//                        StringBuilder stringBuilder = new StringBuilder();
//                        reader.lines().forEach(stringBuilder::append);
//                        builder.value(stringBuilder.toString()).code(Codes.SUCCESS.getValue());
//                        continuationPossible = true;
//                    } catch (IOException ex){
//                        builder.code(Codes.READING_EXCEPTION.getValue());
//                        ex.printStackTrace();
//                    }
//                } else {
//                    builder.code(Codes.NULL_STREAM.getValue());
//                }
//            } catch (IOException ex){
//                builder.code(Codes.READING_EXCEPTION.getValue());
//                ex.printStackTrace();
//            }
//        } else {
//            builder.code(Codes.NO_PATH.getValue());
//        }
//
//        context.put(key, Properties.RESULT.getValue(), builder.success(continuationPossible).build());
//    }
//
//    @RequiredArgsConstructor
//    @Getter
//    public enum Properties{
//        PATH("task.fileReading.property.path"),
//        RESULT("task.fileReading.property.result");
//
//        private final String value;
//    }
//
//    @RequiredArgsConstructor
//    @Getter
//    public enum Codes {
//        NO_PATH("task.fileReading.code.noPath"),
//        READING_EXCEPTION("task.fileReading.readingException"),
//        NULL_STREAM("task.fileReading.nullStream"),
//        SUCCESS("task.fileReading.success");
//
//        private final String value;
//    }
//}
