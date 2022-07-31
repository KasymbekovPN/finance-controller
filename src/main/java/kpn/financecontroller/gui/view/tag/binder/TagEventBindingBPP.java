package kpn.financecontroller.gui.view.tag.binder;

import kpn.financecontroller.gui.event.tag.controller.TagControllerNotificationEvent;
import kpn.financecontroller.gui.event.tag.controller.TagAfterDeletingEvent;
import kpn.financecontroller.gui.event.tag.controller.TagAfterSavingEvent;
import kpn.financecontroller.gui.event.tag.form.TagCancelButtonClickEvent;
import kpn.financecontroller.gui.event.tag.form.TagDeleteButtonOnClickEvent;
import kpn.financecontroller.gui.event.tag.form.TagSaveButtonOnClickEvent;
import kpn.financecontroller.gui.event.tag.view.TagViewNotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationService;
import kpn.financecontroller.gui.notifications.NotificationServiceImpl;
import kpn.financecontroller.gui.form.TagForm;
import kpn.financecontroller.gui.view.TagView;
import kpn.financecontroller.gui.controller.TagViewController;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public final class TagEventBindingBPP implements BeanPostProcessor {

    private NotificationService notificationService;
    private TagViewController dataController;
    private TagForm form;
    private TagView view;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        setNotificationService(bean);
        setDataController(bean);
        setForm(bean);
        setView(bean);
        bind();

        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    private void setNotificationService(Object bean) {
        if (bean.getClass().equals(NotificationServiceImpl.class)){
            notificationService = (NotificationService) bean;
        }
    }

    private void setDataController(Object bean) {
        if (bean.getClass().equals(TagViewController.class)){
            dataController = (TagViewController) bean;
        }
    }

    private void setForm(Object bean) {
        if (bean.getClass().equals(TagForm.class)){
            form = (TagForm) bean;
        }
    }

    private void setView(Object bean) {
        if (bean.getClass().equals(TagView.class)){
            view = (TagView) bean;
        }
    }

    private void bind() {
        if (form != null && view != null && dataController != null){

            form.addListener(TagSaveButtonOnClickEvent.class, dataController::handleSavingEvent);
            dataController.addListener(TagAfterSavingEvent.class, view::handleSavingEvent);
            form.addListener(TagDeleteButtonOnClickEvent.class, dataController::handleDeletingEvent);
            dataController.addListener(TagAfterDeletingEvent.class, view::handleDeletingEvent);
            form.addListener(TagCancelButtonClickEvent.class, view::handleCancelEvent);
            dataController.addListener(TagControllerNotificationEvent.class, notificationService::notify);
            view.addListener(TagViewNotificationEvent.class, notificationService::notify);

            reset();
        }
    }

    private void reset() {
        view = null;
        form = null;
    }
}
