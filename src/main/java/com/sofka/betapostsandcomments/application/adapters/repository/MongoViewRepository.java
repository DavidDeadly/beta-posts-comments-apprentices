package com.sofka.betapostsandcomments.application.adapters.repository;


import com.google.gson.Gson;
import com.sofka.betapostsandcomments.business.gateways.DomainViewRepository;
import com.sofka.betapostsandcomments.business.gateways.model.CommentViewModel;
import com.sofka.betapostsandcomments.business.gateways.model.PostViewModel;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
public class MongoViewRepository implements DomainViewRepository {
    private final ReactiveMongoTemplate template;

    private final Gson gson = new Gson();

    public MongoViewRepository(ReactiveMongoTemplate template) {
        this.template = template;
    }

    @Override
    public Mono<PostViewModel> findByAggregateId(String aggregateId) {
        /**Make the implementation, using the template, to find a post by its aggregateId*/
        return Mono.just(aggregateId)
                .map(strID -> new Query(Criteria.where("PostID").is(strID)))
                .flatMap(query -> template.findOne(query, PostViewModel.class));
    }

    @Override
    public Flux<PostViewModel> findAllPosts() {
        /**make the implementation, using the template, of the method find all posts that are stored in the db*/
        return template.findAll(PostViewModel.class);
    }

    @Override
    public Mono<PostViewModel> saveNewPost(PostViewModel post) {
        /** make the implementation, using the template, to save a post*/
        return template.save(post);
    }

    public Mono<PostViewModel> addCommentToPost(CommentViewModel comment) {
        /** make the implementation, using the template, to find the post in the database that you want to add the comment to,
         * then add the comment to the list of comments and using the Update class update the existing post
         * with the new list of comments*/

        return
            Mono.just(comment)
            .flatMap(com -> findByAggregateId(com.getPostId()))
            .flatMap(postViewModel -> {
                postViewModel.getComments().add(comment);
                return template.save(postViewModel);
            });
    }
}
