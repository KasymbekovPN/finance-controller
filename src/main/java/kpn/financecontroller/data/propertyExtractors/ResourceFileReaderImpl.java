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
        try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path)){
            if (checkInputStream(inputStream)){
                try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))){
                    StringBuilder stringBuilder = new StringBuilder();
                    reader.lines().forEach(stringBuilder::append);
                    value = stringBuilder.toString();
                } catch (IOException ex){
                    ex.printStackTrace();
                }
            }
        } catch (IOException ex){
            ex.printStackTrace();
        }

        return Result.<String>builder()
                .success(value != null)
                .value(value)
                .code(value != null ? "resource.file.read.success" : "resource.file.read.fail")
                .arg(path)
                .build();
    }

    private boolean checkInputStream(InputStream inputStream) {
        return inputStream != null;
    }
}
