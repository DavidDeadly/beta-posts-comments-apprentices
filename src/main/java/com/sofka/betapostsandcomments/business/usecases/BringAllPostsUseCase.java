package com.sofka.betapostsandcomments.business.usecases;


import com.sofka.betapostsandcomments.application.adapters.repository.MongoViewRepository;
import com.sofka.betapostsandcomments.business.gateways.model.PostViewModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class BringAllPostsUseCase {
    //Finish the implementation of this class using the functional interfaces
  private final MongoViewRepository repository;

  public BringAllPostsUseCase(MongoViewRepository repository) {
    this.repository = repository;
  }

  public Flux<PostViewModel> bringAll() {
    return repository.findAllPosts();
  }
}
