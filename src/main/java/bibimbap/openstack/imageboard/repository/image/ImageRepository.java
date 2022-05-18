package bibimbap.openstack.imageboard.repository.image;

import bibimbap.openstack.imageboard.domain.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageRepository {
    String saveFile(MultipartFile file,String imageName);
    void saveImage(Image image);
    Image getImageById(Long id);
    void deleteImage(Long id);
}
