package bibimbap.openstack.imageboard.service.image;

import bibimbap.openstack.imageboard.domain.image.Image;
import bibimbap.openstack.imageboard.dto.image.ImageUploadDto;
import bibimbap.openstack.imageboard.repository.image.ImageRepository;
import bibimbap.openstack.imageboard.util.file.FileManager;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    @Qualifier
    private final FileManager fileManager;


    public Long saveImage(ImageUploadDto dto) throws JSONException, ParseException, IOException {
        Image image = fileManager.save(dto);
        return imageRepository.save(image).getId();
    }

    public boolean isExistImage(Long id) {
        return !imageRepository.findById(id).isEmpty();
    }

    public Image getImageById(Long id) {
        return imageRepository.findById(id).get();
    }

    public void deleteImageById(Long id) throws JSONException, ParseException {
        Image image = getImageById(id);
        imageRepository.delete(Image.builder().id(id).build());
        fileManager.deleteImage(image.getImageURL());
    }

    public String getContentType(String url) {
        try {
            return fileManager.probeContentType(url);
        } catch (IOException | JSONException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public Resource getResource(String url) throws JSONException, ParseException {
        return fileManager.loadResource(url);
    }

    public byte[] getFile(String url) throws JSONException, ParseException, IOException {
        return fileManager.loadFile(url);
    }
}
