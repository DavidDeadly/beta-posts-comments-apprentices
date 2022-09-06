package com.sofka.betapostsandcomments.domain;

import co.com.sofka.domain.generic.EventChange;
import com.sofka.betapostsandcomments.domain.events.CommentAdded;
import com.sofka.betapostsandcomments.domain.events.PostCreated;
import com.sofka.betapostsandcomments.domain.values.Author;
import com.sofka.betapostsandcomments.domain.values.CommentId;
import com.sofka.betapostsandcomments.domain.values.Content;
import com.sofka.betapostsandcomments.domain.values.Title;

import java.util.ArrayList;

public class PostChange extends EventChange {

    public PostChange(Post post){
        apply((PostCreated event)-> {
            post.title = new Title(event.getTitle());
            post.author = new Author(event.getAuthor());
            post.comments = new ArrayList<>();
        });

        apply((CommentAdded event)-> {
            Comment comment = new Comment(CommentId.of(event.getCommentID()), new Author(event.getAuthor()), new Content(event.getContent()));
            post.comments.add(comment);
        });
    }
}
