package com.sofka.betapostsandcomments.business.usecases;


import com.sofka.betapostsandcomments.business.gateways.DomainViewRepository;
import com.sofka.betapostsandcomments.business.gateways.EventBus;
import com.sofka.betapostsandcomments.business.gateways.model.CommentViewModel;
import com.sofka.betapostsandcomments.business.gateways.model.PostViewModel;
import com.sofka.betapostsandcomments.business.generic.DomainUpdater;
import com.sofka.betapostsandcomments.domain.events.CommentAdded;
import com.sofka.betapostsandcomments.domain.events.PostCreated;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.sofka.betapostsandcomments.application.config.RabbitMqConfig.PROXY_ROUTING_KEY_POST_CREATED;
import static com.sofka.betapostsandcomments.application.config.RabbitMqConfig.PROXY_ROUTING_KEY_COMMENT_ADDED;

import java.util.ArrayList;

@Service
public class ViewUpdater extends DomainUpdater {

  private final EventBus eventBus;

    //Complete the implementation of the view updater
    public ViewUpdater(DomainViewRepository repository, EventBus eventBus) {
      this.eventBus = eventBus;

      updater((PostCreated event) -> Mono.just(event)
      .map(ev -> {
        PostViewModel postNew = new PostViewModel(
                ev.aggregateRootId(),
                ev.getAuthor(),
                ev.getTitle(),
                new ArrayList<>());

        eventBus.publish(postNew, PROXY_ROUTING_KEY_POST_CREATED);

        return postNew;
      })
      .flatMap(repository::saveNewPost)
      .subscribe());

      updater((CommentAdded event) -> Mono.just(event)
      .map(ev -> {
        CommentViewModel newComment = new CommentViewModel(
                ev.getCommentID(),
                ev.aggregateRootId(),
                ev.getAuthor(),
                ev.getContent());

        eventBus.publish(newComment, PROXY_ROUTING_KEY_COMMENT_ADDED);

        return newComment;
      })
      .flatMap(repository::addCommentToPost)
      .subscribe());
    }
}
