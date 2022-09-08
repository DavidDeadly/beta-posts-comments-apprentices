package com.sofka.betapostsandcomments.business.gateways;

public interface EventBus {
    <M> void publish(M model, String routingKey);

    void publishError(Throwable errorEvent);
}
