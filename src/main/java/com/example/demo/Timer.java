package com.example.demo;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Component
public class Timer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private InputStreamReader reader;
    private BufferedReader br;

    public Timer() {
        this.reader =new InputStreamReader(System.in);
        this.br = new BufferedReader(reader);
    }

    @Scheduled(fixedDelay = 1000L)
    public void setMessage(){
        String message=readMessage();
        rabbitTemplate.convertAndSend(DemoApplication.exchangeName,"clients.kirill",
                DemoApplication.queueName+"-> "+message);
    }

    private String readMessage(){
        try{
            String input=br.readLine();
            if("bb".equals(input)){
                System.out.println("Exit");
                System.exit(0);
            }
            return input;
        }
        catch (IOException e){
            e.printStackTrace();
            System.exit(0);
            return null;
        }
    }
}
