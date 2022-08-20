package kpn.financecontroller.gui.binding;

import kpn.financecontroller.gui.controller.ViewController;
import kpn.financecontroller.gui.form.Form;
import kpn.financecontroller.gui.notifications.NotificationService;
import kpn.financecontroller.gui.view.GridView;
import kpn.lib.domain.Domain;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.util.function.Function;

public abstract class EventBindingBPP<D extends Domain<Long>> implements BeanPostProcessor {
    protected NotificationService notificationService;
    protected ViewController<D> viewController;
    protected Form<D> form;
    protected GridView<D> gridView;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        startSetterChain(bean);
        bind();
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    private void startSetterChain(Object bean) {
        createChainLink().start(bean);
    }

    private void bind() {
        if (checkBindingCondition()){
            doBinding();
            reset();
        }
    }

    protected boolean checkBindingCondition(){
        return notificationService != null &&
                viewController != null &&
                form != null &&
                gridView != null;
    }

    protected void reset() {
        gridView = null;
        form = null;
    }

    protected Function<Object, Boolean> createNotificationServiceChainLink(Class<? extends NotificationService> type) {
        return  (Object bean) -> {
            if (bean.getClass().equals(type)){
                notificationService = type.cast(bean);
                return true;
            }
            return false;
        };
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

    protected abstract void doBinding();
    protected abstract ChainLink createChainLink();

    @RequiredArgsConstructor
    protected static class ChainLink {
        private final Function<Object, Boolean> setter;
        private ChainLink next;
        public void start(Object bean) {
            if (!setter.apply(bean) && next != null){
                next.start(bean);
            }
        }

        public ChainLink addNext(Function<Object, Boolean> setter){
            if (next == null){
                next = new ChainLink(setter);
            } else {
                next.addNext(setter);
            }
            return this;
        }
    }
}
