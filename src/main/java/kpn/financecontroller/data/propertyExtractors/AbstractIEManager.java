package kpn.financecontroller.data.propertyExtractors;

import kpn.financecontroller.result.Result;

import java.util.Arrays;

abstract public class AbstractIEManager<K, E> implements IEManager<K, E> {

    private final ResourceFileReader reader;
    private final String name;

    protected String directory;
    protected Result<Void> result;

    public AbstractIEManager(ResourceFileReader reader, String name) {
        this.reader = reader;
        this.name = name;
    }

    @Override
    public IEManager<K, E> directory(String directory) {
        this.directory = directory;
        return this;
    }

    @Override
    public IEManager<K, E> run( /* !!! CollectorFiller */) {
        if (result == null){
            result = checkDirectory();//CollectorFiller
            if (result.getSuccess()){
                Result<String> strResult = calculatePath();//CollectorFiller
                if (strResult.getSuccess()){
                    strResult = read(strResult.getValue());
                    result = strResult.getSuccess()
                            ? fillCollector(strResult.getValue())//CollectorFiller
                            : convertResult(strResult);
                } else {
                    result = convertResult(strResult);
                }
            }
        }
        return this;
    }

    @Override
    public Result<Void> get() {
        if (result == null){
            result = createNotInitResult();
        }
        return result;
    }

    protected Result<Void> checkDirectory() {
        boolean success = directory != null;
        Result.Builder<Void> builder = Result.<Void>builder()
                .success(success);
        if (!success){
            builder.code("inManager.checking.directory.null");
        }
        return builder.build();
    }

    protected Result<String> calculatePath() {
        return Result.<String>builder()
                .success(false)
                .code("ieManager.method.calculatePath.notImplemented")
                .build();
    }

    protected Result<Void> fillCollector(String content) {
        return Result.<Void>builder()
                .success(false)
                .code("ieManager.method.fillCollector.notImplemented")
                .build();
    }

    private Result<Void> convertResult(Result<String> inResult) {
        Result.Builder<Void> builder = Result.<Void>builder()
                .success(inResult.getSuccess())
                .code(inResult.getCode());
        Arrays.stream(inResult.getArgs()).forEach(builder::arg);

        return builder.build();
    }

    private Result<Void> createNotInitResult() {
        return Result.<Void>builder()
                .success(false)
                .code("ieManager.getting.fail.notInit")
                .arg(name)
                .build();
    }

    private Result<String> read(String path) {
        return reader.read(path);
    }
}
