package com.sofka.betapostsandcomments.business.usecases;

import com.sofka.betapostsandcomments.application.adapters.repository.MongoViewRepository;
import com.sofka.betapostsandcomments.business.gateways.model.PostViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BringPostByIdTest {
  @Mock
  private MongoViewRepository repository;

  private BringPostById useCase;

  @BeforeEach
  void init() {
    useCase = new BringPostById(repository);
  }

  @Test
  void bringPostByIdTest() {
    final String POST_ID = "36126321";
    final String AUTHOR = "DavidDeadly";
    final String TITLE = "Days with no end";

    PostViewModel postViewModel = new PostViewModel(
            POST_ID,
            AUTHOR,
            TITLE,
            new ArrayList<>());

    BDDMockito.when(repository.findByAggregateId(BDDMockito.anyString()))
            .thenReturn(Mono.just(postViewModel));

    Mono<PostViewModel> post = useCase.bring(POST_ID);

    StepVerifier
            .create(post)
            .assertNext(postView -> {
              assertEquals(POST_ID, postView.getPostID());
              assertEquals(AUTHOR, postView.getAuthor());
              assertEquals(TITLE, postView.getTitle());
            })
            .expectComplete()
            .log()
            .verify();


    BDDMockito.verify(repository).findByAggregateId(BDDMockito.anyString());

  }
}