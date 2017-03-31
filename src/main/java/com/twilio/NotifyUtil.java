package com.twilio;

import com.twilio.sdk.Twilio;
import com.twilio.sdk.creator.notifications.v1.service.NotificationCreator;
import com.twilio.sdk.resource.notifications.v1.service.Notification;

public class NotifyUtil {

    public static void main(String args[]) {
        // Authenticate with Twilio
        Twilio.init(System.getenv("TWILIO_ACCOUNT_SID"), System.getenv("TWILIO_AUTH_TOKEN"));

        //Send a notification
        String serviceSid = System.getenv("TWILIO_NOTIFICATION_SERVICE_SID");

        try {
            NotificationCreator creator = Notification.create(serviceSid);
            creator.setIdentity(args[0]);
            creator.setBody("Hello World!");
            Notification notification = creator.execute();
            System.out.println(notification.toString());
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
