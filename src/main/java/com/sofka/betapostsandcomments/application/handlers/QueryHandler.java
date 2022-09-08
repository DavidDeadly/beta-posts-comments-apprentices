package com.sofka.betapostsandcomments.application.handlers;


import com.sofka.betapostsandcomments.business.usecases.BringAllPostsUseCase;
import com.sofka.betapostsandcomments.business.usecases.BringPostById;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class QueryHandler {

    //Create a route that allows you to make a Get Http request that brings you all the posts and also a post by its id
  @Bean
  public RouterFunction<ServerResponse> getAllPosts(BringAllPostsUseCase useCase) {
    return route(
          GET("/posts"),
          request ->
              useCase.bringAll()
              .switchIfEmpty(Mono.error(new Throwable()))
              .collectList()
              .flatMap(postViewModels ->
                      ServerResponse.ok()
                      .contentType(MediaType.APPLICATION_JSON)
                      .body(BodyInserters.fromValue(postViewModels)))
              .onErrorResume(throwable -> ServerResponse.noContent().build())
    );
  }

  @Bean
  public RouterFunction<ServerResponse> getPostById(BringPostById useCase) {
    return route(
            GET("/post/{id}"),
            request ->
                useCase.bring(request.pathVariable("id"))
                .switchIfEmpty(Mono.error(new Throwable()))
                .flatMap(postViewModel ->
                        ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(postViewModel)))
                .onErrorResume(throwable -> ServerResponse.notFound().build())
    );
  }
}
