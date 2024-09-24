package com.compass.RabbitMQApp.queue;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DeadLetterQueue {

    @Bean
    TopicExchange exchangeDLX() {
        return new TopicExchange(DefaultQueue.topicExchangeName + ".dlx");
    }

    @Bean
    Queue deadLetterQueues() {
        return new Queue(DefaultQueue.queueName + ".dlq", true);
    }

    @Bean
    Binding bindingDLQ(Queue deadLetterQueues, TopicExchange exchangeDLX) {
        return BindingBuilder.bind(deadLetterQueues).to(exchangeDLX).with("test.queue.dlk"); // Ensure this matches DLK
    }
}
