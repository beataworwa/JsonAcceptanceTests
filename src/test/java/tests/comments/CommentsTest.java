package tests.comments;

import model.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

public class CommentsTest {

    private static final String URL_GET_ALL_COMMENTS = "https://jsonplaceholder.typicode.com/comments";
    private static final String URL_GET_COMMENTS_FOR_POST=URL_GET_ALL_COMMENTS+"?postId={id}";
    private static final int COMMENTS_AMOUNT = 500;
    private static final int COMMENTS_FOR_POST_AMOUNT=5;
    private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void getAllComments() {
        Comment[] result = restTemplate.getForObject(URL_GET_ALL_COMMENTS, Comment[].class);
        assertThat(result.length).isEqualTo(COMMENTS_AMOUNT);
    }

    @Test
    public void getCommentsForPost(){
        long postId=2L;
        Comment[] result = restTemplate.getForObject(URL_GET_COMMENTS_FOR_POST, Comment[].class,postId);
        assertThat(result.length).isEqualTo(COMMENTS_FOR_POST_AMOUNT);
    }

}
