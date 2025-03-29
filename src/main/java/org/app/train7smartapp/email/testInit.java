//package org.app.train7smartapp.email;
//
//import org.app.train7smartapp.email.client.NotificationClient;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//
//@Component
//public class testInit implements ApplicationRunner {
//
//    private final NotificationClient notificationClient;
//
//    @Autowired
//    public testInit(NotificationClient notificationClient) {
//        this.notificationClient = notificationClient;
//    }
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//
//        ResponseEntity<String> testNotification = notificationClient.getTestNotification();
//
//        System.out.println(testNotification.getBody());
//
//    }
//}
