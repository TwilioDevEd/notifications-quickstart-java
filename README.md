# Notifications Quickstart for Java

This application should give you a ready-made starting point for integrating notifications into your
own apps with Twilio Notifications. Before we begin, we need to collect
 the credentials we need to run the application - you will need credentials for either (or both) of Apple or Google's push notification service:

Credential | Description
---------- | -----------
Twilio Account SID | Your main Twilio account identifier - [find it on your dashboard](https://www.twilio.com/console).
Twilio APN Credential SID | Adds iOS notification ability to your app - [generate one here](https://www.twilio.com/console/chat/credentials). You'll need to provision your APN push credentials to generate this. See [this](https://www.twilio.com/docs/api/chat/guides/push-notifications-ios) guide on how to do that. (Optional)
Twilio GCM Credential SID | Adds Android/GCM notification ability to your app - [generate one here](https://www.twilio.com/console/chat/credentials). You'll need to provision your GCM push credentials to generate this. See [this](https://www.twilio.com/docs/api/chat/guides/push-notifications-android) guide on how to do that. (Optional)
Twilio Notification_Service SID | Use the CreateServiceUtil class to generate this. Just run 'java -cp target/notification-quickstart-1.0-SNAPSHOT.jar com.twilio.CreateServiceUtil' in your terminal, after you add the above configuration values to the `.env` file.

# Setting up the Java Application

This application uses the lightweight [Spark Framework](www.sparkjava.com), and
requires Java 8. You'll also need a working copy of [Maven](https://maven.apache.org/index.html)

Begin by creating a configuration file for your application:

```bash
cp .env.example .env
```

Edit `.env` with the four configuration parameters we gathered from above. Export
the configuration in this file as system environment variables like so on Unix
based systems:

```bash
source .env
```

Next, we need to install our dependencies from Maven:

```bash
mvn install
```

And compile our application code:

```bash
mvn package
```

Now we should be all set! Run the application using the `java -jar` command.

```bash
java -jar target/notification-quickstart-1.0-SNAPSHOT.jar
```

Your application should now be running at [http://localhost:4567/](http://localhost:4567/).

# Usage

When your app receives a 'registration' in the form of a POST request to the /register endpoint from a mobile client, it will create a binding. A binding is the address Twilio gives your app installation. It lets our service know where to send notifications.  

To send a notification to the client run the notify utility class

```bash
  java -cp target/notification-quickstart-1.0-SNAPSHOT.jar com.twilio.NotifyUtil IDENTITY_HERE
```

The mobile client will receive a notification with the hardcoded 'Hello {IDENTITY}' message.

That's it! Check out our REST API [docs](http://www.twilio.com/docs/api/notifications/rest/overview) for more information on Twilio Notifications.

## License

MIT
