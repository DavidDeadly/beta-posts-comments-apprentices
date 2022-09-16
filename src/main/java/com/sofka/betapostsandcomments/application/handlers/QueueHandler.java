package com.sofka.betapostsandcomments.application.handlers;



import co.com.sofka.domain.generic.DomainEvent;
import com.google.gson.Gson;
import com.sofka.betapostsandcomments.application.adapters.bus.Notification;
import com.sofka.betapostsandcomments.business.usecases.UpdateViewUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@Slf4j
public class QueueHandler implements Consumer<String> {
    private final Gson gson = new Gson();
    private final String ALPHA_PACKAGE_STRUCTURE = "com.sofka.alphapostcomments";
    private final String BETA_PACKAGE_STRUCTURE = "com.sofka.betapostsandcomments";
    private final UpdateViewUseCase useCase;

    public QueueHandler(UpdateViewUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public void accept(String received) {
        Notification notification = Notification.from(received);

        log.info("[Notification Received]: " + notification);

        String classLocation = notification.getType().replace(ALPHA_PACKAGE_STRUCTURE, BETA_PACKAGE_STRUCTURE);

        try {
            DomainEvent event = (DomainEvent) gson.fromJson(notification.getBody(), Class.forName(classLocation));
            useCase.accept(event);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
