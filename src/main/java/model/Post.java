package model;


import javafx.geometry.Pos;

public class Post {

    private Long userId;
    private Long id;
    private String body;
    private String title;

    public Post(){
    }
    public Post(Long userId,Long id,String body,String title) {
        this.userId=userId;
        this.id=id;
        this.body=body;
        this.title=title;
    }


    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }


    public Long getUserId() {
        return userId;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }


}
