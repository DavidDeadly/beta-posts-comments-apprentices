package com.sofka.betapostsandcomments.business.usecases;

import com.sofka.betapostsandcomments.application.adapters.repository.MongoViewRepository;
import com.sofka.betapostsandcomments.business.gateways.DomainViewRepository;
import com.sofka.betapostsandcomments.business.gateways.EventBus;
import com.sofka.betapostsandcomments.business.gateways.model.CommentViewModel;
import com.sofka.betapostsandcomments.business.gateways.model.PostViewModel;
import com.sofka.betapostsandcomments.domain.events.CommentAdded;
import com.sofka.betapostsandcomments.domain.events.PostCreated;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UpdateViewUseCaseTest {

  @Mock
  private EventBus eventBus;

  @Mock
  private DomainViewRepository repository;

  @InjectMocks
  private ViewUpdater viewUpdater;

  private UpdateViewUseCase useCase;

  @BeforeEach
  void init() {
    useCase = new UpdateViewUseCase(this.viewUpdater);
  }

  @Test
  void postCreatedTest() {
    final String POST_ID = "36126321";
    final String AUTHOR = "DavidDeadly";
    final String TITLE = "Days with no end";

    PostViewModel postViewModel = new PostViewModel(
            POST_ID,
            AUTHOR,
            TITLE,
            new ArrayList<>());

    PostCreated event = new PostCreated(
            TITLE,
            AUTHOR
    );

    BDDMockito
            .when(repository.saveNewPost(ArgumentMatchers.any(PostViewModel.class)))
            .thenReturn(Mono.just(postViewModel));

    useCase.accept(event);

    BDDMockito.verify(repository, BDDMockito.times(1))
            .saveNewPost(ArgumentMatchers.any(PostViewModel.class));
    BDDMockito.verify(eventBus, BDDMockito.times(1))
            .publish(ArgumentMatchers.any(PostViewModel.class), ArgumentMatchers.anyString());
  }

  @Test
  void commentAddedTest() {
    final String COMMENT_ID = "82137123";
    final String POST_ID = "36126321";
    final String AUTHOR = "DavidDeadly";
    final String TITLE = "Days with no end";
    final String CONTENT = "Days with no end";

    PostViewModel postViewModel = new PostViewModel(
            POST_ID,
            AUTHOR,
            TITLE,
            new ArrayList<>());

    CommentAdded event = new CommentAdded(
            COMMENT_ID,
            AUTHOR,
            CONTENT
    );

    BDDMockito
            .when(repository.addCommentToPost(ArgumentMatchers.any(CommentViewModel.class)))
            .thenReturn(Mono.just(postViewModel));

    useCase.accept(event);

    BDDMockito.verify(repository, BDDMockito.times(1))
            .addCommentToPost(ArgumentMatchers.any(CommentViewModel.class));
    BDDMockito.verify(eventBus, BDDMockito.times(1))
            .publish(ArgumentMatchers.any(PostViewModel.class), ArgumentMatchers.anyString());
  }

}