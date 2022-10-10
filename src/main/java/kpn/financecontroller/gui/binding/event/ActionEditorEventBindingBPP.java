package kpn.financecontroller.gui.binding.event;

import kpn.financecontroller.data.domain.Action;
import kpn.financecontroller.gui.event.action.editor.ActionEditorNotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationServiceImpl;
import kpn.financecontroller.gui.view.ActionEditor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public final class ActionEditorEventBindingBPP extends EventBindingBPP<Action> {
    private ActionEditor actionEditor;

    @Override
    protected void doBinding() {
        actionEditor.addListener(ActionEditorNotificationEvent.class, notificationService::notify);
    }

    @Override
    protected ChainLink createChainLink() {
        return new ChainLink(createNotificationServiceChainLink(NotificationServiceImpl.class))
                .addNext(createActionEditorChainLink());
    }

    @Override
    protected void reset() {
        super.reset();
        actionEditor = null;
    }

    @Override
    protected boolean checkBindingCondition() {
        return notificationService != null && actionEditor != null;
    }

    private Function<Object, Boolean> createActionEditorChainLink() {
        return (Object bean) -> {
            if (bean.getClass().equals(ActionEditor.class)){
                actionEditor = (ActionEditor) bean;
                return true;
            }
            return false;
        };
    }
}
