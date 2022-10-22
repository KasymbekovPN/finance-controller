package kpn.financecontroller.gui.binding.event;

import kpn.financecontroller.gui.event.action.display.ActionDisplayNotificationEvent;
import kpn.financecontroller.gui.view.ActionDisplay;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public final class ActionDisplayEventBindingBPP extends NotificationEventBindingBPP{
    private ActionDisplay actionDisplay;

    @Override
    protected ChainLink createChainLink() {
        return super.createChainLink()
                .addNext(createActionDisplayChainLink());
    }

    @Override
    protected boolean checkBindingCondition() {
        return super.checkBindingCondition() &&
                actionDisplay != null;
    }

    @Override
    protected void doBinding() {
        super.doBinding();
        actionDisplay.addListener(ActionDisplayNotificationEvent.class, notificationService::notify);
    }

    @Override
    protected void reset() {
        super.reset();
        actionDisplay = null;
    }

    private Function<Object, Boolean> createActionDisplayChainLink(){
        return (Object bean) -> {
            if (bean.getClass().equals(ActionDisplay.class)){
                actionDisplay = (ActionDisplay) bean;
                return true;
            }
            return false;
        };
    }
}
