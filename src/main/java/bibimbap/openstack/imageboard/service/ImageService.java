package bibimbap.openstack.imageboard.service;

import bibimbap.openstack.imageboard.domain.Image;
import bibimbap.openstack.imageboard.domain.Post;
import bibimbap.openstack.imageboard.dto.ImageUploadDto;
import bibimbap.openstack.imageboard.repository.image.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    private final String uploadDir = "/Users/jaeminan/desktop/uploaded/";

    public void saveImage(ImageUploadDto dto) {
        String url = _saveFile(dto);
        imageRepository.save(Image.builder()
                .imageName(dto.getImageName())
                .imageURL(url)
                .build()
        );
    }

    private String _saveFile(ImageUploadDto dto) {
        MultipartFile mptFile = dto.getFile();
        String imageName = dto.getImageName();
        String filePath = _getFilePath(dto);
        File file = new File(filePath);
        try {
            mptFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }

    private String _getFilePath(ImageUploadDto dto) {
        MultipartFile mptFile = dto.getFile();
        String imageName = dto.getImageName();

        String ogName = mptFile.getOriginalFilename();
        String suffix = ogName.split("\\.")[1];

        StringBuilder sb = new StringBuilder();
        String newName = sb.append(imageName).append(".").append(suffix).toString();
        dto.setImageName(newName);

        sb = new StringBuilder();
        return sb.append(uploadDir).append(newName).toString();
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
}
