package bibimbap.openstack.imageboard.repository.image;

import bibimbap.openstack.imageboard.domain.image.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
