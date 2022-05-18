package bibimbap.openstack.imageboard.service;

import bibimbap.openstack.imageboard.domain.Post;
import bibimbap.openstack.imageboard.dto.PostCreateDto;
import bibimbap.openstack.imageboard.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void createPost(PostCreateDto post) {
        postRepository.save(_postDtoConvertor(post));
    }

    public boolean isExistPost(Long id) {
        return postRepository.getPostById(id) != null;
    }

    public Post readPostById(Long id) {
        return postRepository.getPostById(id);
    }

    public boolean isValidPage(Long page) {
        return postRepository.getListLength() / 10 >= page;
    }

    public List<Post> readPostList(Long page) {
        return postRepository.getPostList(page);
    }

    public void update(Post post) {
        postRepository.update(post);
    }

    public void delete(Long id) {
        postRepository.delete(id);
    }

    private Post _postDtoConvertor(PostCreateDto dto) {
        return new Post(null, dto.getUser_id(), dto.getTitle(), dto.getContent(), dto.getImg());
    }
}
