package bibimbap.openstack.imageboard.service.post;

import bibimbap.openstack.imageboard.domain.member.Member;
import bibimbap.openstack.imageboard.domain.post.Post;
import bibimbap.openstack.imageboard.dto.image.ImageUploadDto;
import bibimbap.openstack.imageboard.dto.post.PostCreateDto;
import bibimbap.openstack.imageboard.dto.post.PostReadDto;
import bibimbap.openstack.imageboard.repository.member.MemberRepository;
import bibimbap.openstack.imageboard.repository.post.PostRepository;
import bibimbap.openstack.imageboard.service.image.ImageService;
import bibimbap.openstack.imageboard.util.file.FileManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final MemberRepository memberRepository;
    private final ImageService imageService;
    private final PostRepository postRepository;

    public Long createPost(PostCreateDto post) throws JSONException, ParseException, IOException {
        // image save

        Long imageId = null;
        if (post.getFile() != null) {
            imageId = imageService.saveImage(ImageUploadDto.builder()
                    .imageName(post.getImageName())
                    .file(post.getFile())
                    .build()
            );
        }

        log.info("DTO = {}", post);
        Member member = memberRepository.findById(post.getUserId()).get();

        Post build = Post.builder()
                .member(member)
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

    public PostReadDto readPostById(Long id) {
        Post post = postRepository.findById(id).get();
        Member member = post.getMember();
        return PostReadDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .imgId(post.getImgId())
                .userId(member.getId())
                .username(member.getUsername())
                .build();
    }

    public boolean isValidPage(Long page) {
        return postRepository.count() / 10 >= page;
    }

    public List<Post> readPostList(Pageable pageable) {
        return postRepository.findAll(pageable).getContent();
    }

    public Long getListLength() {
        return postRepository.count();
    }

    public void update(Post post) {
        postRepository.save(post);
    }

    public void delete(Long id) {
        Post post = postRepository.findById(id).get();
        postRepository.delete(Post.builder().id(id).build());
        imageService.deleteImageById(post.getImgId());
    }

}
