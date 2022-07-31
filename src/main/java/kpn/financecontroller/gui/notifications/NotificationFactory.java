package kpn.financecontroller.gui.notifications;

import org.springframework.stereotype.Service;

// TODO: 31.07.2022 del
@Service
public class NotificationFactory {

    public NotificationBuilder getBuilder(Notifications type){
        return new NotificationBuilderImpl(type);
    }
}
