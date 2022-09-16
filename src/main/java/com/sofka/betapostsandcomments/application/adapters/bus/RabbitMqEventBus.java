package com.sofka.betapostsandcomments.application.adapters.bus;


import com.google.gson.Gson;
import com.sofka.betapostsandcomments.application.config.RabbitMqConfig;
import com.sofka.betapostsandcomments.business.gateways.EventBus;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
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

        log.info("[Model Sent]: " + model);

        rabbitTemplate.convertAndSend(
                RabbitMqConfig.EXCHANGE, routingKey,
                gson.toJson(model).getBytes()
        );
    }

    @Override
    public void publishError(Throwable errorEvent) {

    }
}
