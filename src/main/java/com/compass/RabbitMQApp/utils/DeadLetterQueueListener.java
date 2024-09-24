package com.compass.RabbitMQApp.utils;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DeadLetterQueueListener {

    private static final String DLQ = "spring-boot.dlq";
    private static final String X_RETRY_HEADER = "x-retry";
    @RabbitListener(queues = DLQ)
    public void processamentoDeErros(String message, @Headers Map<String, Object> headers){
        Integer tries = (Integer) headers.get(X_RETRY_HEADER);

        System.out.println("Reprocessando mensagem que deu erro!");

        if(tries == null){
            tries = 0;
        }
        if(tries <= 4){
            tries += 1;
            Map<String, Object> headerUpdate = new HashMap<>(headers);

            headerUpdate.put(X_RETRY_HEADER, tries);

            System.out.println("Aplicando lógica de reprocessamento");]

        }


    }
}
