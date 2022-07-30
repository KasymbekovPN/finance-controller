package kpn.financecontroller.gui.views.tag.controller;

import com.querydsl.core.types.Predicate;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.gui.events.DeleteFormEvent;
import kpn.financecontroller.gui.events.SaveFormEvent;
import kpn.financecontroller.gui.notifications.NotificationFactory;
import kpn.financecontroller.gui.notifications.Notifications;
import kpn.financecontroller.gui.views.MainLayout;
import kpn.financecontroller.gui.views.tag.TagForm;
import kpn.financecontroller.gui.views.tag.event.TagDeleteReactionEvent;
import kpn.financecontroller.gui.views.tag.event.TagSaveReactionEvent;
import kpn.financecontroller.rfunc.checker.removing.RemovingChecker;
import kpn.financecontroller.rfunc.checker.saving.SavingChecker;
import kpn.lib.result.Result;
import kpn.lib.service.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Component
@com.vaadin.flow.component.Tag(com.vaadin.flow.component.Tag.OBJECT)
public final class TagDataController extends Component {
    @Autowired
    private SavingChecker<Tag> savingChecker;
    @Autowired
    private RemovingChecker<Tag> removingChecker;
    @Autowired
    private NotificationFactory notificationFactory;
    @Autowired
    private Service<Long, Tag, Predicate, Result<List<Tag>>> tagService;

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    public void handleSavingEvent(SaveFormEvent<TagForm, Tag> event){
        Tag domain = event.getValue();
        Result<List<Tag>> result = savingChecker.apply(domain);
        if (result.isSuccess()){
            result = tagService.saver().save(domain);
        }
        createNotification(result);
        fireEvent(new TagSaveReactionEvent(this, domain));
    }

    public void handleDeletingEvent(DeleteFormEvent<TagForm, Tag> event){
        Tag domain = event.getValue();
        Result<List<Tag>> result = removingChecker.apply(domain);
        if (result.isSuccess()){
            result = tagService.deleter().byId(domain.getId());
        }
        createNotification(result);
        fireEvent(new TagDeleteReactionEvent(this, domain));
    }

    // TODO: 30.07.2022 bean or by event
    private void createNotification(Result<List<Tag>> result) {
        if (!result.isSuccess()){
            String text = getTranslation(result.getSeed().getCode(), result.getSeed().getArgs());
            notificationFactory.getBuilder(Notifications.ERROR)
                    .duration(60_000)
                    .text(text)
                    .buttonIcon(new Icon("lumo", "cross"))
                    .buttonThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE)
                    .buttonAttribute("aria-label", "Close")
                    .build()
                    .open();
        }
    }
}
