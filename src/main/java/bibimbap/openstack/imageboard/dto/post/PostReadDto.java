package bibimbap.openstack.imageboard.dto.post;

import lombok.*;

@Getter @Setter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostReadDto {
    private Long id;
    private String title;
    private String content;
    private Long imgId;
    private Long userId;
    private String username;
}
