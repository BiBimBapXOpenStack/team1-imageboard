package bibimbap.openstack.imageboard.controller.post;

import bibimbap.openstack.imageboard.domain.post.Post;
import bibimbap.openstack.imageboard.dto.post.PostCreateDto;
import bibimbap.openstack.imageboard.dto.post.PostReadDto;
import bibimbap.openstack.imageboard.service.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    // Create
    @PostMapping("/")
    public ResponseEntity createPost(@ModelAttribute PostCreateDto post) {
        postService.createPost(post);
        return new ResponseEntity(HttpStatus.OK);
    }

    // getLength
    @GetMapping("/length")
    public ResponseEntity getListLength() {
        return new ResponseEntity(postService.getListLength(), HttpStatus.OK);
    }

    // Read

    @Transactional
    @GetMapping("/{_id}")
    public ResponseEntity readPostById(@PathVariable Long _id) {

        if (!postService.isExistPost(_id)) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        PostReadDto post = postService.readPostById(_id);
        log.info("post = {}", post);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    // Read List
    @Transactional
    @GetMapping("/")
    public ResponseEntity<List<Post>> readPostList(Pageable pageable) {
        if (!postService.isValidPage((long) pageable.getPageNumber())) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(postService.readPostList(pageable), HttpStatus.OK);
    }

    // Update
    @PutMapping("/{_id}")
    public ResponseEntity updatePost(@RequestBody Post post,@PathVariable Long _id) {
        if (!postService.isExistPost(_id)) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        post.setId(_id);
        postService.update(post);
        return new ResponseEntity(HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/{_id}")
    public ResponseEntity deletePost(@PathVariable Long _id) {
        if (!postService.isExistPost(_id)) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        postService.delete(_id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
