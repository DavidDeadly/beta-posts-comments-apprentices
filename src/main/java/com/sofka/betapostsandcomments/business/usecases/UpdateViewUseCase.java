package com.sofka.betapostsandcomments.business.usecases;

import co.com.sofka.domain.generic.DomainEvent;
import com.sofka.betapostsandcomments.business.generic.DomainUpdater;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class UpdateViewUseCase implements Consumer<DomainEvent> {
  private final DomainUpdater updater;

  public UpdateViewUseCase(DomainUpdater updater) {
    this.updater = updater;
  }
  @Override
  public void accept(DomainEvent event) {
    updater.applyEvent(event);
  }
}
