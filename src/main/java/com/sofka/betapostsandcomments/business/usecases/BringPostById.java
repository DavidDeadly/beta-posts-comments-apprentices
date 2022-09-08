package com.sofka.betapostsandcomments.business.usecases;



import com.sofka.betapostsandcomments.application.adapters.repository.MongoViewRepository;
import com.sofka.betapostsandcomments.business.gateways.model.PostViewModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class BringPostById {
    // finish the implementation of this class using the functional interfaces
  private final MongoViewRepository repository;

  public BringPostById(MongoViewRepository repository) {
    this.repository = repository;
  }

  public Mono<PostViewModel> bring(String postId) {
    return repository.findByAggregateId(postId);
  }
}
