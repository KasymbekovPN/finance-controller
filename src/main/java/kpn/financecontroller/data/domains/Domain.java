package kpn.financecontroller.data.domains;

import java.util.Queue;

public interface Domain {
    String getInfo();
    String get(Queue<String> path);
}
