package kpn.financecontroller.gui.binding.event;

import kpn.financecontroller.gui.notifications.NotificationService;
import kpn.financecontroller.gui.notifications.NotificationServiceImpl;

import java.util.function.Function;

public abstract class NotificationEventBindingBPP extends BaseEventBindingBPP {
    protected NotificationService notificationService;

    @Override
    protected ChainLink createChainLink() {
        return new ChainLink(createNotificationChainLink());
    }

    @Override
    protected boolean checkBindingCondition() {
        return notificationService != null;
    }

    private Function<Object, Boolean> createNotificationChainLink() {
        return  (Object bean) -> {
            if (bean.getClass().equals(NotificationServiceImpl.class)){
                notificationService = (NotificationServiceImpl) bean;
                return true;
            }
            return false;
        };
    }
}
