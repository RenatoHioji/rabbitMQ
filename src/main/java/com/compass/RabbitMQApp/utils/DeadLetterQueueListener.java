package com.compass.RabbitMQApp.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DeadLetterQueueListener {

    private final RabbitTemplate rabbitTemplate;
    private static final String DLQ = "spring-boot.dlq";
    private static final String PARKINGQUEUE = "spring-boot.dlq.parking-lot";
    private static final String X_RETRY_HEADER = "x-retry";

    @RabbitListener(queues = DLQ)
    public void processamentoDeErros(String message, @Headers Map<String, Object> headers){
        Integer  retryHeader = (Integer) headers.get(X_RETRY_HEADER);
        if(retryHeader == null){
            retryHeader = 0;
        }
        System.out.println("Reprocessando mensagem");
        if(retryHeader < 3){
            int tryCount = retryHeader +1;
            Map<String, Object> updatedHeader = new HashMap<>(headers);
            updatedHeader.put(X_RETRY_HEADER, tryCount);

            final MessagePostProcessor messagePostProcessor = message1 -> {
                MessageProperties messageProperties = message1.getMessageProperties();
                updatedHeader.forEach(messageProperties::setHeader);
                return message1;
            };

            this.rabbitTemplate.convertAndSend(DLQ, (Object) message, messagePostProcessor);
         }else{
            System.out.println("Reprocessamento falhou");
            this.rabbitTemplate.convertAndSend(PARKINGQUEUE, message);
        }

    }
}
