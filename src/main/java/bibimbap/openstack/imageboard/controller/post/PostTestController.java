package bibimbap.openstack.imageboard.controller.post;

import bibimbap.openstack.imageboard.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
@RequestMapping("/api/posts")
public class PostTestController {
    private final PostService postService;

    @GetMapping("/deleteTest")
    public String deleteTestColumn() {
        postService.deleteAllTestColumn();
        return "DELETE SUCCESS";
    }
}
