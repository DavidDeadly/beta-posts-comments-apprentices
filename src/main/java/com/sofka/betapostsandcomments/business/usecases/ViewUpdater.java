package com.sofka.betapostsandcomments.business.usecases;


import com.sofka.betapostsandcomments.business.gateways.DomainViewRepository;
import com.sofka.betapostsandcomments.business.gateways.EventBus;
import com.sofka.betapostsandcomments.business.gateways.model.CommentViewModel;
import com.sofka.betapostsandcomments.business.gateways.model.PostViewModel;
import com.sofka.betapostsandcomments.business.generic.DomainUpdater;
import com.sofka.betapostsandcomments.domain.events.CommentAdded;
import com.sofka.betapostsandcomments.domain.events.PostCreated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.sofka.betapostsandcomments.application.config.RabbitMqConfig.PROXY_ROUTING_KEY_POST_CREATED;
import static com.sofka.betapostsandcomments.application.config.RabbitMqConfig.PROXY_ROUTING_KEY_COMMENT_ADDED;

import java.util.ArrayList;

@Service
@Slf4j
public class ViewUpdater extends DomainUpdater {
    //Complete the implementation of the view updater
    public ViewUpdater(DomainViewRepository repository, EventBus eventBus) {

      updater((PostCreated event) -> Mono.just(event)
      .map(ev -> {
        log.info("[Event Extracted]: " + ev);
        return new PostViewModel(
                ev.aggregateRootId(),
                ev.getAuthor(),
                ev.getTitle(),
                new ArrayList<>());})
      .flatMap(postNew ->
              repository.saveNewPost(postNew)
              .doOnNext(postViewModel -> eventBus.publish(postNew, PROXY_ROUTING_KEY_POST_CREATED)))
      .subscribe());

      updater((CommentAdded event) -> Mono.just(event)
      .map(ev -> {
        log.info("[Event Extracted]: " + ev);
        return new CommentViewModel(
                ev.getCommentID(),
                ev.aggregateRootId(),
                ev.getAuthor(),
                ev.getContent());})
        .flatMap(newComment ->
                repository.addCommentToPost(newComment)
                .doOnNext(postViewModel -> eventBus.publish(newComment, PROXY_ROUTING_KEY_COMMENT_ADDED)))
        .subscribe());
    }
}
