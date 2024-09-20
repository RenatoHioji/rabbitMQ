package com.compass.RabbitMQApp;

import com.compass.RabbitMQApp.utils.MessageReceiver;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MessagingWithSpring {

    public static final String topicExchangeName = "spring-boot-exchange";

    static final String queueName = "spring-boot";

    // Minha queue padrão
    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    // Usando Dead Letter Queue
    @Bean
    Queue myQueue(){
        return QueueBuilder.durable("myQueue")
                .withArgument("x-dead-letter-exchange", "exchange-dead-letter")
                .withArgument("x-dead-letter-routing-key", "queue-dead-letter")
                .build();
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("test.queue.*");
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(MessageReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }
}
