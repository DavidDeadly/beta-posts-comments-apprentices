package com.sofka.betapostsandcomments.business.gateways.model;

import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
public class PostViewModel {
    private String id;
    private String postID;
    private String author;
    private String title;
    private List<CommentViewModel> comments;

    public PostViewModel() {
        this.comments = new ArrayList<>();
    }

    public PostViewModel(String postID, String author, String title, List<CommentViewModel> comments) {
        this.postID = postID;
        this.author = author;
        this.title = title;
        this.comments = comments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<CommentViewModel> getComments() {
        return comments;
    }

    public void setComments(List<CommentViewModel> comments) {
        this.comments = comments;
    }
}
