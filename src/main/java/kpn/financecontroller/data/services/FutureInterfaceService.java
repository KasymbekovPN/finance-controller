package kpn.financecontroller.data.services;

import java.util.concurrent.Future;

public interface FutureInterfaceService<I, O> {
    Future<O> calculate(I input);
}
