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
class BringAllPostsUseCaseTest {

  @Mock
  private MongoViewRepository repository;

  private BringAllPostsUseCase useCase;

  @BeforeEach
  void init() {
    useCase = new BringAllPostsUseCase(repository);
  }

  @Test
  void bringAllPostsUseCase() {
    PostViewModel post1 = new PostViewModel(
            "2838213",
            "David",
            "Life",
            new ArrayList<>());

    PostViewModel post2 = new PostViewModel(
            "8387132",
            "Isabel",
            "Death",
            new ArrayList<>());

    PostViewModel post3 = new PostViewModel(
            "28312372",
            "Cris",
            "Soccer",
            new ArrayList<>());

    BDDMockito.when(repository.findAllPosts())
            .thenReturn(Flux.just(post1, post2, post3));

    Mono<List<PostViewModel>> posts = useCase.bringAll()
            .collectList();

    StepVerifier
            .create(posts)
            .assertNext(postViewModels -> {
              assertEquals(3, postViewModels.size());
            })
            .expectComplete()
            .log()
            .verify();


    BDDMockito.verify(repository).findAllPosts();

  }

}