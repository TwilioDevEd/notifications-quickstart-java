package com.twilio;

import com.twilio.sdk.Twilio;
import com.twilio.sdk.creator.notifications.v1.ServiceCreator;
import com.twilio.sdk.resource.notifications.v1.Service;

public class CreateServiceUtil {
    public static void main(String args[]) {

        // Authenticate with Twilio
        Twilio.init(System.getenv("TWILIO_ACCOUNT_SID"), System.getenv("TWILIO_AUTH_TOKEN"));


        // Create a user notification service instance
        ServiceCreator creator = Service.create();
        creator.setFriendlyName("My First Notifications App");

        // APN Credentials
        String apnCredentialSid = System.getenv("TWILIO_APN_CREDENTIAL_SID");
        if (apnCredentialSid != null) {
            creator.setApnCredentialSid(apnCredentialSid);
            System.out.println("Adding APN Credentials to service");
        } else {
            System.out.println("No APN Credentials configured - add in .env, if available.");
        }

        // GCM Credentials
        String gcmCredentialSid = System.getenv("TWILIO_GCM_CREDENTIAL_SID");
        if (gcmCredentialSid != null) {
            creator.setGcmCredentialSid(gcmCredentialSid);
            System.out.println("Adding GCM Credentials to service");
        } else {
            System.out.println("No GCM Credentials configured - add in .env, if available.");
        }

        Service service = creator.execute();
        System.out.println(service.toString());

    }
}

