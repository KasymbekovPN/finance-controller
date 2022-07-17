package support;

import kpn.lib.exception.DTOException;

import java.util.Arrays;

public final class DTOExceptionChecker {

    public boolean check(DTOException ex, String code, String... args){
        return ex.getMessage().equals(code) && Arrays.equals(ex.getArgs(), args);
    }
}
