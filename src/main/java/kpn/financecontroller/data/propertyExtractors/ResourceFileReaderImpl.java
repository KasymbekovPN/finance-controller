package kpn.financecontroller.data.propertyExtractors;

import kpn.financecontroller.result.Result;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Component
@Profile("dev")
public class ResourceFileReaderImpl implements ResourceFileReader {
    @Override
    public synchronized Result<String> read(String path) {
        String value = null;
        String code = "resource.file.read.fail";
        boolean success = false;
        if (path != null){
            try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path)){
                if (checkInputStream(inputStream)){
                    try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))){
                        StringBuilder stringBuilder = new StringBuilder();
                        reader.lines().forEach(stringBuilder::append);
                        value = stringBuilder.toString();
                        success = true;
                        code = "resource.file.read.success";
                    } catch (IOException ex){
                        ex.printStackTrace();
                    }
                }
            } catch (IOException ex){
                ex.printStackTrace();
            }
        } else {
            code = "resource.file.read.fail.nullPath";
        }


        return Result.<String>builder()
                .success(success)
                .value(value)
                .code(code)
                .arg(path)
                .build();
    }

    private boolean checkInputStream(InputStream inputStream) {
        return inputStream != null;
    }
}
