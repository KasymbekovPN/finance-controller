package kpn.financecontroller.gui.notifications;

import org.springframework.stereotype.Service;

@Service
public class NotificationFactory {

    public NotificationBuilder getBuilder(Notifications type){
        return new NotificationBuilderImpl(type);
    }
}
