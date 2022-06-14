package bibimbap.openstack.imageboard.dto.post;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class PostUpdateDto {
    private Long id;
    private Long userId;
    private String title;
    private String content;

    private Long imgId;
}
