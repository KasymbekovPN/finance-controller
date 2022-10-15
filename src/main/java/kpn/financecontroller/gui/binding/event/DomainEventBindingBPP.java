package kpn.financecontroller.gui.binding.event;

import kpn.financecontroller.gui.controller.ViewController;
import kpn.financecontroller.gui.form.Form;
import kpn.financecontroller.gui.view.GridView;
import kpn.lib.domain.Domain;

import java.util.function.Function;

public abstract class DomainEventBindingBPP<D extends Domain<Long>> extends NotificationEventBindingBPP {
    protected ViewController<D> viewController;
    protected Form<D> form;
    protected GridView<D> gridView;

     @Override
     protected boolean checkBindingCondition() {
          return super.checkBindingCondition() &&
                  viewController != null &&
                  form != null &&
                  gridView != null;
     }

     @Override
     protected void reset() {
          super.reset();
          gridView = null;
          form = null;
     }

    protected Function<Object, Boolean> createViewControllerChainLink(Class<? extends ViewController<D>> type) {
        return  (Object bean) -> {
            if (bean.getClass().equals(type)){
                viewController = type.cast(bean);
                return true;
            }
            return false;
        };
    }

    protected Function<Object, Boolean> createFormChainLink(Class<? extends Form<D>> type) {
        return  (Object bean) -> {
            if (bean.getClass().equals(type)){
                form = type.cast(bean);
                return true;
            }
            return false;
        };
    }

    protected Function<Object, Boolean> createGridViewChainLink(Class<? extends GridView<D>> type) {
        return  (Object bean) -> {
            if (bean.getClass().equals(type)){
                gridView = type.cast(bean);
                return true;
            }
            return false;
        };
    }
}
