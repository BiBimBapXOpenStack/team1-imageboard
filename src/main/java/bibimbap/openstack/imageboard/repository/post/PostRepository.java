package bibimbap.openstack.imageboard.repository.post;

import bibimbap.openstack.imageboard.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long> {
    void deleteAllByTitle(String title);
}
