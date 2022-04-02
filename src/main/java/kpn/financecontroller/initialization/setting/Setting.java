package kpn.financecontroller.initialization.setting;

import kpn.financecontroller.initialization.generators.valued.Entities;

public interface Setting {
    boolean isEnable();
    String getPath(Entities entity);
}
