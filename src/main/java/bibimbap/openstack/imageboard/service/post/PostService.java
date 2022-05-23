package bibimbap.openstack.imageboard.service.post;

import bibimbap.openstack.imageboard.domain.post.Post;
import bibimbap.openstack.imageboard.dto.image.ImageUploadDto;
import bibimbap.openstack.imageboard.dto.post.PostCreateDto;
import bibimbap.openstack.imageboard.repository.post.PostRepository;
import bibimbap.openstack.imageboard.service.image.ImageService;
import bibimbap.openstack.imageboard.util.file.FileManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final ImageService imageService;
    private final PostRepository postRepository;

    public Long createPost(PostCreateDto post) {
        // image save

        Long imageId = null;
        if (post.getFile() != null) {
            imageId = imageService.saveImage(ImageUploadDto.builder()
                    .imageName(post.getImageName())
                    .file(post.getFile())
                    .build()
            );
        }

        Post build = Post.builder()
                .userId(post.getUserId())
                .title(post.getTitle())
                .content(post.getContent())
                .imgId(imageId)
                .build();

        return postRepository.save(build).getId();
    }

    public boolean isExistPost(Long id) {
        Optional<Post> post = postRepository.findById(id);
        return !post.isEmpty();
    }

    public Post readPostById(Long id) {
        return postRepository.findById(id).get();
    }

    public boolean isValidPage(Long page) {
        return postRepository.count() / 10 >= page;
    }

    public List<Post> readPostList(Pageable pageable) {
        return postRepository.findAll(pageable).getContent();
    }

    public void update(Post post) {
        postRepository.save(post);
    }

    public void delete(Long id) {
        postRepository.delete(Post.builder().id(id).build());
    }

}
