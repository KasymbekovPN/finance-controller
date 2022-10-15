package kpn.financecontroller.gui.binding.event;

import kpn.financecontroller.gui.event.action.editor.ActionEditorNotificationEvent;
import kpn.financecontroller.gui.view.ActionEditor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public final class ActionEditorEventBindingBPP extends NotificationEventBindingBPP {
    private ActionEditor actionEditor;

    @Override
    protected ChainLink createChainLink() {
        return super.createChainLink()
                .addNext(createActionEditorChainLink());
    }

    @Override
    protected boolean checkBindingCondition() {
        return super.checkBindingCondition() &&
                actionEditor != null;
    }

    @Override
    protected void doBinding() {
        super.doBinding();
        actionEditor.addListener(ActionEditorNotificationEvent.class, notificationService::notify);
    }

    @Override
    protected void reset() {
        super.reset();
        actionEditor = null;
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
