package bibimbap.openstack.imageboard.dto.post;

import lombok.*;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class PostCreateDto {
    private Long userId;
    private String title;
    private String content;
    /**
     * img = tmp -> 나중에 image 파일로 변경
     */
    private String img;
}
