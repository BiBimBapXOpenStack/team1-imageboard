package bibimbap.openstack.imageboard.repository.post;

import bibimbap.openstack.imageboard.domain.Post;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class LocalPostRepository implements PostRepository{

    private static Map<Long, Post> store = new HashMap<>();
    private static Long sequence = 0L;

    @Override
    public void save(Post post) {
        post.setId(++sequence);
        store.put(post.getId(), post);
    }

    @Override
    public Post getPostById(Long _id) {
        return store.get(_id);
    }

    @Override
    public Integer getListLength() {
        return store.size();
    }

    @Override
    public List<Post> getPostList(Long page) {
        return new ArrayList<Post>(store.values());
    }

    @Override
    public void update(Post post) {
        store.put(post.getId(), post);
    }

    @Override
    public void delete(Long _id) {
        store.remove(_id);
    }
}
