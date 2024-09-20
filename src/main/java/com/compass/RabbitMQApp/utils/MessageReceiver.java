package com.compass.RabbitMQApp.utils;

import org.springframework.stereotype.Component;

@Component
public class MessageReceiver {

    public void receiveMessage(String message){
        try{
            System.out.println("Processando mensagem: "+ message);

        }catch(Exception e){
            System.out.println("Realizando processo em caso de erro");
        }
    }

}
