package com.twilio;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.afterAfter;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import com.twilio.sdk.Twilio;


import com.twilio.sdk.creator.notifications.v1.service.BindingCreator;
import com.twilio.sdk.resource.notifications.v1.service.Binding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.ModelAndView;
import spark.template.jade.JadeTemplateEngine;

public class NotificationsApp {

    final static Logger logger = LoggerFactory.getLogger(NotificationsApp.class);

    private class BindingRequest {
        String endpoint;
        String identity;
        String bindingType;
        String address;
    }

    private static class BindingResponse {
        String message;
        String error;
    }

    public static void main(String[] args) {

        // Set up configuration from environment variables
        Map<String, String> configuration = new HashMap<>();
        configuration.put("TWILIO_ACCOUNT_SID", System.getenv("TWILIO_ACCOUNT_SID"));
        configuration.put("TWILIO_AUTH_TOKEN", System.getenv("TWILIO_AUTH_TOKEN"));
        configuration.put("TWILIO_APN_CREDENTIAL_SID", System.getenv("TWILIO_APN_CREDENTIAL_SID"));
        configuration.put("TWILIO_GCM_CREDENTIAL_SID", System.getenv("TWILIO_GCM_CREDENTIAL_SID"));
        configuration.put("TWILIO_NOTIFICATION_SERVICE_SID", System.getenv("TWILIO_NOTIFICATION_SERVICE_SID"));

        // Log all requests and responses
        afterAfter(new LoggingFilter());

        // Basic health check - check environment variables have been configured correctly
        get("/", (request, response) -> new ModelAndView(configuration, "index"), new JadeTemplateEngine());

        post("/register", (request, response) -> {

            // Authenticate with Twilio
            Twilio.init(configuration.get("TWILIO_ACCOUNT_SID"), configuration.get("TWILIO_AUTH_TOKEN"));

            logger.debug(request.body());

            // Decode the JSON Body
            Gson gson = new Gson();
            BindingRequest bindingRequest = gson.fromJson(request.body(), BindingRequest.class);


            // Create a binding
            Binding.BindingType bindingType = Binding.BindingType.forValue(bindingRequest.bindingType);
            BindingCreator creator = Binding.create(configuration.get("TWILIO_NOTIFICATION_SERVICE_SID"),
                    bindingRequest.endpoint, bindingRequest.identity, bindingType, bindingRequest.address);

            try {
                Binding binding = creator.execute();
                logger.info("Binding successfully created");
                logger.debug(binding.toString());


                // Send a JSON response indicating success
                BindingResponse bindingResponse = new BindingResponse();
                bindingResponse.message = "Binding Created";
                response.type("application/json");
                return gson.toJson(bindingResponse);

            } catch (Exception ex) {
                logger.error("Exception creating binding: " + ex.getMessage(), ex);

                // Send a JSON response indicating an error
                BindingResponse bindingResponse = new BindingResponse();
                bindingResponse.message = "Failed to create binding: " + ex.getMessage();
                bindingResponse.error = ex.getMessage();
                response.type("application/json");
                response.status(500);
                return gson.toJson(bindingResponse);
            }
        });
    }
}
