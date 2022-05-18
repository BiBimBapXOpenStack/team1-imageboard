package bibimbap.openstack.imageboard.service;

import bibimbap.openstack.imageboard.domain.Image;
import bibimbap.openstack.imageboard.dto.ImageUploadDto;
import bibimbap.openstack.imageboard.repository.image.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;


    public void saveImage(ImageUploadDto dto) {
        String path = imageRepository.saveFile(dto.getFile(),dto.getImageName());
        imageRepository.saveImage(new Image(null, dto.getImageName(), path));
    }

    public boolean isExistImage(Long id) {
        return imageRepository.getImageById(id) != null;
    }

    public Image getImageById(Long id) {
        return imageRepository.getImageById(id);
    }

    public void deleteImageById(Long id) {
        imageRepository.deleteImage(id);
    }
}
