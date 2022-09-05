package kpn.financecontroller.data.services.action;

import com.fasterxml.jackson.core.util.BufferRecycler;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ActionWorkerImplTest {


    @Test
    void doSth() {
        String packageName = "kpn/financecontroller/gui/external";
        InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(packageName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        Set<String> collect = reader.lines().collect(Collectors.toSet());
        System.out.println(collect);

        //    public Set<Class> findAllClassesUsingClassLoader(String packageName) {
//        InputStream stream = ClassLoader.getSystemClassLoader()
//                .getResourceAsStream(packageName.replaceAll("[.]", "/"));
//        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
//        return reader.lines()
//                .filter(line -> line.endsWith(".class"))
//                .map(line -> getClass(line, packageName))
//                .collect(Collectors.toSet());
//    }

    }
}