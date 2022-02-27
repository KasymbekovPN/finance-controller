package kpn.financecontroller.initialization.task;

import kpn.financecontroller.initialization.context.Context;
import kpn.financecontroller.result.Result;

public class ReadTask {
    private final String path;
    private boolean continuationPossible;

    public ReadTask(String path) {
        this.path = path;
    }

    public void execute(Context context){
    }

    public boolean isContinuationPossible() {
        return continuationPossible;
    }

    // TODO: 27.02.2022 del
//    public synchronized Result<String> read(String path) {
//        String value = null;
//        String code = "resource.file.read.fail";
//        boolean success = false;
//        if (path != null){
//            try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path)){
//                if (checkInputStream(inputStream)){
//                    try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))){
//                        StringBuilder stringBuilder = new StringBuilder();
//                        reader.lines().forEach(stringBuilder::append);
//                        value = stringBuilder.toString();
//                        success = true;
//                        code = "resource.file.read.success";
//                    } catch (IOException ex){
//                        ex.printStackTrace();
//                    }
//                }
//            } catch (IOException ex){
//                ex.printStackTrace();
//            }
//        } else {
//            code = "resource.file.read.fail.nullPath";
//        }
//
//
//        return Result.<String>builder()
//                .success(success)
//                .value(value)
//                .code(code)
//                .arg(path)
//                .build();
//    }
//
//    private boolean checkInputStream(InputStream inputStream) {
//        return inputStream != null;
//    }
}
