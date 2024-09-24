package com.compass.RabbitMQApp.utils;

import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class MessageReceiver {
    public void receiveMessage(String message){
        try{
            System.out.println("Processando mensagem: "+ message);
            if(Objects.equals(message, "error")){
                throw new RuntimeException("Encaminhando para DLQ");
            }

        }catch (Exception e){
            System.out.println("Exception here");
        }

    }

}
