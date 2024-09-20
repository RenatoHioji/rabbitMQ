package com.compass.RabbitMQApp.utils;

import java.util.concurrent.TimeUnit;

import com.compass.RabbitMQApp.MessagingWithSpring;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;
    private final MessageReceiver receiver;


    @Override
    public void run(String... args) throws Exception {
        System.out.println("Enviando a mensagem...");
        rabbitTemplate.convertAndSend(MessagingWithSpring.topicExchangeName, "test.queue.1", "Minha mensagem!");
    }

}