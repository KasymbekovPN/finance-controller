package kpn.financecontroller.search.type;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class DeepImportDataSearcher implements Searcher<String, String> {
    private final Searcher<String, List<Class<?>>> classSearcher;

    @Override
    public String search(String arg) {
        StringBuilder result = new StringBuilder();
        List<Class<?>> classes = classSearcher.search(arg);
        for (Class<?> aClass : classes) {
            result
                    .append("import ")
                    .append(aClass.getPackage().getName())
                    .append(".")
                    .append(aClass.getSimpleName())
                    .append(";\n");
        }

        return result.toString();
    }
}
