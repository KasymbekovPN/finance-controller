package kpn.financecontroller.gui.views.tag.binder;

import kpn.financecontroller.gui.notifications.NotificationService;
import kpn.financecontroller.gui.notifications.NotificationServiceImpl;
import kpn.financecontroller.gui.views.tag.TagForm;
import kpn.financecontroller.gui.views.tag.TagView;
import kpn.financecontroller.gui.views.tag.controller.TagDomainController;
import kpn.financecontroller.gui.views.tag.event.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public final class TagBPP implements BeanPostProcessor {

    private NotificationService notificationService;
    private TagDomainController dataController;
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
        if (bean.getClass().equals(TagDomainController.class)){
            dataController = (TagDomainController) bean;
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
            dataController.addListener(TagSaveReactionEvent.class, view::handleSavingEvent);
            form.addListener(TagDeleteButtonOnClickEvent.class, dataController::handleDeletingEvent);
            dataController.addListener(TagDeleteReactionEvent.class, view::handleDeletingEvent);
            form.addListener(TagCancelButtonClickEvent.class, view::handleCancelEvent);
            dataController.addListener(TagDataControllerNotificationEvent.class, notificationService::notify);
            view.addListener(TagViewNotificationEvent.class, notificationService::notify);

            reset();
        }
    }

    private void reset() {
        view = null;
        form = null;
    }
}
