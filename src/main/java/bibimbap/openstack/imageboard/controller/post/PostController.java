package bibimbap.openstack.imageboard.controller.post;

import bibimbap.openstack.imageboard.domain.post.Post;
import bibimbap.openstack.imageboard.dto.post.PostCreateDto;
import bibimbap.openstack.imageboard.dto.post.PostReadDto;
import bibimbap.openstack.imageboard.dto.post.PostUpdateDto;
import bibimbap.openstack.imageboard.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    // Create
    @PostMapping("/")
    public ResponseEntity createPost(HttpServletRequest request, @ModelAttribute PostCreateDto post) throws JSONException, ParseException, IOException {
        postService.createPost(post,request);
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
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    // Read List
    @Transactional
    @GetMapping("/")
    public ResponseEntity<List<Post>> readPostList() {
        List<Post> posts = postService.readPostList();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    // Read List with Pagination
    @Transactional
    @GetMapping(value = "/", params = {"page", "size"})
    public ResponseEntity<List<Post>> readPostList(Pageable pageable) {

        if (!postService.isValidPage((long) pageable.getPageNumber())) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        List<Post> posts = postService.readPostList(pageable);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }


    // Update
    @PutMapping("/{_id}")
    public ResponseEntity updatePost(@RequestBody PostUpdateDto dto, @PathVariable Long _id) {
        if (!postService.isExistPost(_id)) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        postService.update(dto);
        return new ResponseEntity(HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/{_id}")
    public ResponseEntity deletePost(@PathVariable Long _id) throws JSONException, ParseException {
        if (!postService.isExistPost(_id)) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        postService.delete(_id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
