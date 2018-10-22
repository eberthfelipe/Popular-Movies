package com.android.udacity.popularmovies.Object;

public class MovieReview {
    private String id;
    private String author;
    private String content;
    private String url;

    public MovieReview() {
        this.id = "";
        this.author = "";
        this.content = "";
        this.url = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
