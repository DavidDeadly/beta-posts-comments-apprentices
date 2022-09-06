package com.sofka.betapostsandcomments.business.usecases;

import co.com.sofka.domain.generic.DomainEvent;
import com.sofka.betapostsandcomments.business.gateways.EventBus;
import com.sofka.betapostsandcomments.business.generic.DomainUpdater;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class UpdateViewUseCase implements Consumer<DomainEvent> {

  private final EventBus eventBus;
  private final DomainUpdater updater;

  public UpdateViewUseCase(EventBus eventBus, DomainUpdater updater) {
    this.eventBus = eventBus;
    this.updater = updater;
  }
  @Override
  public void accept(DomainEvent event) {
    eventBus.publish(event);
    updater.applyEvent(event);
  }
}
