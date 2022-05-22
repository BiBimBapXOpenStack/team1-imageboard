package bibimbap.openstack.imageboard.service.image;

import bibimbap.openstack.imageboard.domain.image.Image;
import bibimbap.openstack.imageboard.dto.image.ImageUploadDto;
import bibimbap.openstack.imageboard.repository.image.ImageRepository;
import bibimbap.openstack.imageboard.util.FileManager;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final FileManager fileManager;


    public void saveImage(ImageUploadDto dto) {
        Image image = fileManager.save(dto);
        imageRepository.save(image);
    }

    public boolean isExistImage(Long id) {
        return !imageRepository.findById(id).isEmpty();
    }

    public Image getImageById(Long id) {
        return imageRepository.findById(id).get();
    }

    public void deleteImageById(Long id) {
        imageRepository.delete(Image.builder().id(id).build());
    }

    public String getContentType(String url) {
        try {
            return fileManager.probeContentType(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Resource getResource(String url) {
        return fileManager.loadResource(url);
    }

    public File getFile(String url) {
        return fileManager.loadFile(url);
    }
}
