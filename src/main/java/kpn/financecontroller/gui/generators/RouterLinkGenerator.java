package kpn.financecontroller.gui.generators;

import com.vaadin.flow.router.RouterLink;

public interface RouterLinkGenerator<T>{
    RouterLink generate(T t);
}
