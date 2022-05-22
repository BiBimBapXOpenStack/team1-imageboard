package bibimbap.openstack.imageboard.util;

import bibimbap.openstack.imageboard.domain.image.Image;
import bibimbap.openstack.imageboard.dto.image.ImageUploadDto;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class LocalFileManager implements FileManager{

    private final String uploadDir = "/Users/jaeminan/desktop/uploaded/";

    @Override
    public Image save(ImageUploadDto dto) {
        MultipartFile mptFile = dto.getFile();
        String filePath = _getFilePath(dto);
        File file = new File(filePath);
        try {
            mptFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Image.builder()
                .imageName(dto.getImageName())
                .imageURL(filePath)
                .build();
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

    @Override
    public File loadFile(String url) {
        return new File(url);
    }

    @Override
    public Resource loadResource(String url) {
        return new FileSystemResource(url);
    }

    @Override
    public String probeContentType(String url) throws IOException {
        return Files.probeContentType(Paths.get(url));
    }
}
