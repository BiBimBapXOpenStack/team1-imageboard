package bibimbap.openstack.imageboard.repository.post;

import bibimbap.openstack.imageboard.domain.Post;

import java.util.ArrayList;
import java.util.List;

public interface PostRepository {
    void save(Post post);
    Post getPostById(Long _id);
    Integer getListLength();
    List<Post> getPostList(Long page);
    void update(Post post);
    void delete(Long _id);
}
