package kpn.financecontroller.data.services.action;

import groovy.lang.GroovyShell;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ActionWorkerImpl implements ActionWorker {
    private final GroovyShell shell = new GroovyShell();
    private final String header;

    @Override
    public Object execute(String algorithm) {
        return shell.evaluate(header + "\n" + algorithm);
    }
}
