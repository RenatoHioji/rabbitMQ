package com.compass.RabbitMQApp.queue;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MessageReceiver implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) {
        try {
            // Convert the message body to a String
            String msgBody = new String(message.getBody());
            System.out.println("Received message: " + msgBody);

            // Simulate an error condition for testing
            if ("error".equals(msgBody)) {
                throw new RuntimeException("Simulated processing error");
            }

            // Acknowledge the message after successful processing
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            System.err.println("Error processing message: " + e.getMessage());

            try {
                // Reject the message and route it to DLQ
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false); // false means do not re-queue
            } catch (IOException ioException) {
                System.err.println("Error rejecting message: " + ioException.getMessage());
            }
        }
    }
}
