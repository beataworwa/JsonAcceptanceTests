package tests.posts;

import model.Comment;
import model.Post;
import model.PostBuilder;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

public class PostsTest {

    private static final int POSTS_AMOUNT = 100;
    private static final String URL_CONCRETE_POST = "https://jsonplaceholder.typicode.com/posts/{id}";
    private static final String URL_ALL_POSTS = "https://jsonplaceholder.typicode.com/posts";
    private static final String URL_POSTS_FOR_USER = "https://jsonplaceholder.typicode.com/posts?userId={id}";
    private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void verifyGetAllPosts() {
        Post[] result = restTemplate.getForObject(URL_ALL_POSTS, Post[].class);
        assertThat(result.length).isEqualTo(POSTS_AMOUNT);
    }

    @Test
    public void verifyGetFirstPost() {
        long postId = 1L;
        long userId = 1L;
        Post resultPost = restTemplate.getForObject(URL_CONCRETE_POST, Post.class, postId);
        assertThat(resultPost.getId()).isEqualTo(postId);
        assertThat(resultPost.getUserId()).isEqualTo(userId);
        assertThat(resultPost.getBody()).isEqualTo("quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto");
        assertThat(resultPost.getTitle()).isEqualTo("sunt aut facere repellat provident occaecati excepturi optio reprehenderit");
    }

    @Test
    public void verifyGetPostsForFirstUser() {
        Post[] userPosts = restTemplate.getForObject(URL_POSTS_FOR_USER, Post[].class, 1L);
        assertThat(userPosts.length).isEqualTo(10);
        assertUserPosts(userPosts, 1L);
    }


    @Test
    public void verifyGetPostsForThirdUser() {
        Post[] userPosts = restTemplate.getForObject(URL_POSTS_FOR_USER, Post[].class, 3L);
        assertThat(userPosts.length).isEqualTo(10);
    }

    @Test
    public void verifyCreateNewPostForUser() {
        Post newPost = PostBuilder.getInstance().setBody("my new post").setUserId(5L).setTitle("new post").createPost();
        Post result = restTemplate.postForObject(URL_ALL_POSTS, newPost, Post.class);
        assertThat(result).isNotNull();
    }

    @Test
    public void updateSecondPostWithPutMethod() {
        String newBody = "test update with put";
        String newTitle = "updated post with put";
        Post updatedPost = PostBuilder.getInstance().setBody(newBody).setTitle(newTitle).setUserId(1L).createPost();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Post> entity = new HttpEntity<>(updatedPost, headers);
        ResponseEntity<Post> response = restTemplate.exchange(URL_CONCRETE_POST, HttpMethod.PUT, entity, Post.class, 2L);
        Post resultPost = response.getBody();
        assertThat(resultPost.getId()).isEqualTo(2L);
        assertThat(resultPost.getUserId()).isEqualTo(1L);
        assertThat(resultPost.getBody()).isEqualTo(newBody);
        assertThat(resultPost.getTitle()).isEqualTo(newTitle);
    }

    @Test
    public void getCommentsForPost() {
        long postId = 1L;
        String url = "https://jsonplaceholder.typicode.com/posts/{id}/comments";
        Comment[] resultPost = restTemplate.getForObject(url, Comment[].class, postId);
        assertThat(resultPost.length).isEqualTo(5);
        assertThat(resultPost[0].getEmail()).isEqualTo("Eliseo@gardner.biz");
        assertThat(resultPost[1].getEmail()).isEqualTo("Jayne_Kuhic@sydney.com");
        assertThat(resultPost[2].getEmail()).isEqualTo("Nikita@garfield.biz");
        assertThat(resultPost[3].getEmail()).isEqualTo("Lew@alysha.tv");
        assertThat(resultPost[4].getEmail()).isEqualTo("Hayden@althea.biz");

    }

    @Test
    public void deletePost() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Post updatedPost = PostBuilder.getInstance().createPost();
        HttpEntity<Post> entity = new HttpEntity<>(updatedPost, headers);
        ResponseEntity<Post> response = restTemplate.exchange(URL_CONCRETE_POST, HttpMethod.DELETE, entity, Post.class, 50L);
        Post resultPost = response.getBody();
        assertThat(resultPost).isNotNull();
        assertThat(resultPost.getId()).isNull();
    }

    private void assertUserPosts(final Post[] userPosts, final long firstExpectedId) {
        long expectedId = firstExpectedId;
        for (Post onePost : userPosts) {
            assertThat(onePost.getId()).isEqualTo(expectedId);
            assertThat(onePost.getUserId()).isEqualTo(1L);
            expectedId++;
        }
    }
}
