package bibimbap.openstack.imageboard.domain;

import lombok.*;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class Image {
    private Long id;
    private String imageName;
    private String imageURL;
}
