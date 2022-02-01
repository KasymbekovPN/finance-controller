package kpn.financecontroller.data.propertyExtractors;

import kpn.financecontroller.result.Result;
import lombok.Setter;

import java.io.*;

abstract public class AbstractIEManager<K, E> implements IEManager<K, E> {
    @Setter
    protected String directory;

    private Result<Void> result;

    @Override
    public Result<Void> run() {
        if (result == null){
            result = calculateResult();
        }

//        Result<Void> result = checkDirectoryValue();
//        if (result.getSuccess()){
//            String path = calculatePath();
//            Result<String> contentGettingResult = getFileContent(path);
//
//        }

//        Result<Void> result = checkDirectoryValue();
//        if (result.getSuccess()){
//            String path = calculatePath();
//            try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path)){
//                result = checkInputStream(inputStream);
//                if (result.getSuccess()){
//                    try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))){
//                        StringBuilder stringBuilder = new StringBuilder();
//                        reader.lines().forEach(stringBuilder::append);
//                        return fillCollector(stringBuilder.toString());
//                    } catch (IOException ex){
//                        // TODO: 31.01.2022 code
//                    }
//                }
//            } catch (IOException ex){
//                //< code
//            }
//        }

        return result;
    }

    private Result<Void> calculateResult() {
        return null;
    }

//    // TODO: 31.01.2022 impl here
//    protected abstract Result<Void> checkDirectoryValue();
//    protected abstract Result<Void> checkInputStream(InputStream inputStream);
//
////    private boolean checkDirectoryValue() {
////        return directory != null;
////    }
////
////    private boolean checkInputStream(InputStream inputStream) {
////        return inputStream != null;
////    }
//
//    protected abstract String calculatePath();
//    protected abstract Result<Void> fillCollector(String toString);
}
