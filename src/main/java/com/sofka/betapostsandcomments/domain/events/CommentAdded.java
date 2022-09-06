package com.sofka.betapostsandcomments.domain.events;

import co.com.sofka.domain.generic.DomainEvent;

public class CommentAdded extends DomainEvent {

    private String commentID;
    private String author;
    private String content;


    public CommentAdded(String commentID, String author, String content) {
        super("posada.santiago.commentcreated");
        this.commentID = commentID;
        this.author = author;
        this.content = content;
    }

    public String getCommentID() {
        return commentID;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
