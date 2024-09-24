package com.compass.RabbitMQApp.utils;

import com.compass.RabbitMQApp.queue.DefaultQueue;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;


    @Override
    public void run(String... args) throws Exception {
        rabbitTemplate.convertAndSend(DefaultQueue.topicExchangeName, "test.queue.1", "Minha mensagem!");
        rabbitTemplate.convertAndSend(DefaultQueue.topicExchangeName, "test.queue.1", "error");
    }

}