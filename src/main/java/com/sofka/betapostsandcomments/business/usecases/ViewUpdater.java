package com.sofka.betapostsandcomments.business.usecases;


import com.sofka.betapostsandcomments.business.gateways.DomainViewRepository;
import com.sofka.betapostsandcomments.business.gateways.model.CommentViewModel;
import com.sofka.betapostsandcomments.business.gateways.model.PostViewModel;
import com.sofka.betapostsandcomments.business.generic.DomainUpdater;
import com.sofka.betapostsandcomments.domain.events.CommentAdded;
import com.sofka.betapostsandcomments.domain.events.PostCreated;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Service
public class ViewUpdater extends DomainUpdater {

    //Complete the implementation of the view updater
    public ViewUpdater(DomainViewRepository repository) {

      updater((PostCreated event) -> Mono.just(event)
      .map(ev -> new PostViewModel(
              ev.aggregateRootId(),
              ev.getAuthor(),
              ev.getTitle(),
              new ArrayList<>()))
      .flatMap(repository::saveNewPost)
      .subscribe());

      updater((CommentAdded event) -> Mono.just(event)
      .map(ev -> new CommentViewModel(
              ev.getCommentID(),
              ev.aggregateRootId(),
              ev.getAuthor(),
              ev.getContent()))
      .flatMap(repository::addCommentToPost)
      .subscribe());
    }
}
