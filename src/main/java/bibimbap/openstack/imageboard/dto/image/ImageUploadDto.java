package bibimbap.openstack.imageboard.dto.image;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class ImageUploadDto {
    private String imageName;
    private MultipartFile file;
}
