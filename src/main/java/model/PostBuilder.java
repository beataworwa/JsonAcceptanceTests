package model;

public class PostBuilder {
    private Long userId;
    private Long id;
    private String body;
    private String title;

    public static PostBuilder getInstance() {
        return new PostBuilder();
    }

    public PostBuilder setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public PostBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public PostBuilder setBody(String body) {
        this.body = body;
        return this;
    }

    public PostBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public Post createPost() {
        return new Post(userId, id, body, title);
    }
}