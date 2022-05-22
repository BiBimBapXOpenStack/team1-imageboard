package bibimbap.openstack.imageboard.repository.image;

import bibimbap.openstack.imageboard.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
