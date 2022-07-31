package kpn.financecontroller.gui.controller;

import com.querydsl.core.types.Predicate;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.shared.Registration;
import kpn.financecontroller.gui.event.DeleteFormEvent;
import kpn.financecontroller.gui.event.SaveFormEvent;
import kpn.financecontroller.gui.form.Form;
import kpn.financecontroller.rfunc.checker.removing.RemovingChecker;
import kpn.financecontroller.rfunc.checker.saving.SavingChecker;
import kpn.lib.domain.Domain;
import kpn.lib.result.Result;
import kpn.lib.seed.Seed;
import kpn.lib.service.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class ViewController<D extends Domain<Long>> extends Component {
    @Autowired
    private SavingChecker<D> savingChecker;
    @Autowired
    private RemovingChecker<D> removingChecker;
    @Autowired
    private Service<Long, D, Predicate, Result<List<D>>> service;

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    public <F extends Form<D>> void handleSavingEvent(SaveFormEvent<F, D> event){
        D domain = event.getValue();
        Result<List<D>> result = savingChecker.apply(domain);
        if (result.isSuccess()){
            result = service.saver().save(domain);
        }
        sendNotification(result);
        fireEvent(createAfterSavingEvent(domain));
    }

    public <F extends Form<D>> void handleDeletingEvent(DeleteFormEvent<F, D> event){
        D domain = event.getValue();
        Result<List<D>> result = removingChecker.apply(domain);
        if (result.isSuccess()){
            result = service.deleter().byId(domain.getId());
        }
        sendNotification(result);
        fireEvent(createAfterDeletingEvent(domain));
    }

    private void sendNotification(Result<List<D>> result) {
        if (!result.isSuccess()){
            fireEvent(createNotificationEvent(result.getSeed()));
        }
    }

    protected abstract ComponentEvent<?> createAfterSavingEvent(D domain);
    protected abstract ComponentEvent<?> createAfterDeletingEvent(D domain);
    protected abstract ComponentEvent<?> createNotificationEvent(Seed seed);
}
