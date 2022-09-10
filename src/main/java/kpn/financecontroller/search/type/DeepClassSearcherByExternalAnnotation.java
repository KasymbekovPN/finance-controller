package kpn.financecontroller.search.type;

import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public final class DeepClassSearcherByExternalAnnotation implements Searcher<String, List<Class<?>>> {
    private final Class<? extends Annotation> annotation;

    @Override
    public List<Class<?>> search(String arg) {
        List<Class<?>> result = new ArrayList<>();

        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(arg);
        if (is != null){
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            Set<String> names = reader.lines().collect(Collectors.toSet());
            for (String name : names) {
                if (name.endsWith(".class")){
                    Class<?> type = loadClass(arg, name);
                    if (type != null){
                        result.add(type);
                    }
                } else if (!name.contains(".")){
                    result.addAll(search(arg + "/" + name));
                }
            }
        }

        return result;
    }

    private Class<?> loadClass(String arg, String name) {
        String packageName = arg.replace("/", ".");
        try {
            String path = packageName + "." + name.substring(0, name.lastIndexOf("."));
            Class<?> type = Class.forName(path);
            return type.getAnnotation(annotation) != null
                    ? type
                    : null;
        } catch (ClassNotFoundException ignored) {}
        return null;
    }
}
