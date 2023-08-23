package com.akazimour.orderservice.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class StatusMessageConsumer {
    @JmsListener(destination = "orderStatus",containerFactory = "myFactory")
    public void onStatusMessage(String content){

        System.out.println("THIS IS THE STATUS MESSAGE FROM CONSUMER :"+content);


    }
}
