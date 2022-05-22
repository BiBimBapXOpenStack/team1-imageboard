package bibimbap.openstack.imageboard.controller;

import bibimbap.openstack.imageboard.domain.Post;
import bibimbap.openstack.imageboard.dto.PostCreateDto;
import bibimbap.openstack.imageboard.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    // Create
    @PostMapping("/")
    public ResponseEntity createPost(@RequestBody PostCreateDto post) {
        postService.createPost(post);
        return new ResponseEntity(HttpStatus.OK);
    }

    // Read
    @GetMapping("/{_id}")
    public ResponseEntity readPostById(@PathVariable Long _id) {
        if (!postService.isExistPost(_id)) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(postService.readPostById(_id), HttpStatus.OK);
    }

    // Read List
    @GetMapping("/")
    public ResponseEntity<List<Post>> readPostList(Pageable pageable) {
        if (!postService.isValidPage((long) pageable.getPageNumber())) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(postService.readPostList(pageable), HttpStatus.OK);
    }

    // Update
    @PutMapping("/")
    public ResponseEntity updatePost(@RequestBody Post post) {
        if (!postService.isExistPost(post.getId())) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
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
