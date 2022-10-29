package kpn.financecontroller.data.services.action;

import groovy.lang.GroovyShell;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import lombok.RequiredArgsConstructor;

// TODO: 29.10.2022 del
@RequiredArgsConstructor
public final class ActionWorkerOldImpl implements ActionWorkerOld {
    private final GroovyShell shell = new GroovyShell();
    private final String header;

    @Override
    public Result<Object> execute(String algorithm) {
        try{
            Object object = shell.evaluate(header + "\n" + algorithm);
            return ImmutableResult.<Object>ok(object);
        } catch (Throwable t){
            return ImmutableResult.<Object>bFail("gui.text.algorithm.execution.fail")
                    .arg(t.getClass().getSimpleName())
                    .arg(t.getMessage())
                    .build();
        }
    }
}
