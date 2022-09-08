package com.sofka.betapostsandcomments.application.adapters.bus;


import com.google.gson.Gson;
import com.sofka.betapostsandcomments.application.config.RabbitMqConfig;
import com.sofka.betapostsandcomments.business.gateways.EventBus;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqEventBus implements EventBus {
    private final RabbitTemplate rabbitTemplate;
    private final Gson gson = new Gson();

    public RabbitMqEventBus(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    @Override
    public <M> void publish(M model, String routingKey) {
        //Find a way to send this notification through the predefined queues in the rabbitMq configuration,
        //To that specific exchange and queues bases on the type of event
        rabbitTemplate.convertAndSend(
                RabbitMqConfig.EXCHANGE, routingKey,
                gson.toJson(model).getBytes()
        );
    }

    @Override
    public void publishError(Throwable errorEvent) {

    }
}
