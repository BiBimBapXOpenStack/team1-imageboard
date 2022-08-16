package bibimbap.openstack.imageboard.service.post;

import bibimbap.openstack.imageboard.domain.image.Image;
import bibimbap.openstack.imageboard.domain.member.Member;
import bibimbap.openstack.imageboard.domain.post.Post;
import bibimbap.openstack.imageboard.dto.image.ImageUploadDto;
import bibimbap.openstack.imageboard.dto.post.PostCreateDto;
import bibimbap.openstack.imageboard.dto.post.PostReadDto;
import bibimbap.openstack.imageboard.dto.post.PostUpdateDto;
import bibimbap.openstack.imageboard.repository.image.ImageRepository;
import bibimbap.openstack.imageboard.repository.member.MemberRepository;
import bibimbap.openstack.imageboard.repository.post.PostRepository;
import bibimbap.openstack.imageboard.service.image.ImageService;
import bibimbap.openstack.imageboard.util.log.CustomLogger;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final MemberRepository memberRepository;
    private final ImageService imageService;
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;

    public Long createPost(PostCreateDto post, HttpServletRequest request) throws JSONException, ParseException, IOException {
        // image save

        Long imageId = null;
        if (post.getFile() != null) {
            imageId = imageService.saveImage(ImageUploadDto.builder()
                    .imageName(post.getImageName())
                    .file(post.getFile())
                    .build()
            );
            CustomLogger.log(request,"IMAGE/SAVE SUCCESS (imageId:"+imageId+")");
        }


        Member member = memberRepository.findById(post.getUserId()).get();
        Image image = imageRepository.findById(imageId).get();

        Post build = Post.builder()
                .member(member)
                .title(post.getTitle())
                .content(post.getContent())
                .image(image)
                .build();


        Post saved = postRepository.save(build);
        CustomLogger.log(request,"POST/CREATE SUCCESS (postId:"+saved.getId()+",member.email:"+saved.getMember().getEmail()+")");
        return saved.getId();
    }

    public boolean isExistPost(Long id) {
        Optional<Post> post = postRepository.findById(id);
        return !post.isEmpty();
    }

    public PostReadDto readPostById(Long id) {
        Post post = postRepository.findById(id).get();
        Member member = post.getMember();
        Image image = post.getImage();
        return PostReadDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .imageUrl(image.getImageURL())
                .imageId(image.getId())
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

    public List<Post> readPostList() {
        return postRepository.findAll();
    }

    public Long getListLength() {
        return postRepository.count();
    }

    public void update(PostUpdateDto dto) {
        Optional<Post> post = postRepository.findById(dto.getId());

        post.ifPresent(p -> {
            p.setContent(dto.getContent());
            p.setTitle(dto.getTitle());
            postRepository.save(p);
        });
    }

    public void delete(Long id) throws JSONException, ParseException {
        Post post = postRepository.findById(id).get();
        postRepository.delete(Post.builder().id(id).build());
        imageService.deleteImageById(post.getImage().getId());
    }

    public void deleteAllTestColumn() {
        postRepository.deleteAllByTitle("TEST");
    }

}
