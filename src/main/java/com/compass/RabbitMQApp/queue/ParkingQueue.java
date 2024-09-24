package com.compass.RabbitMQApp.queue;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ParkingQueue {

    @Bean
    public Queue parkingLotDLQ() {
        return new Queue(DefaultQueue.queueName + ".dlq.parking-lot", true);
    }
}
