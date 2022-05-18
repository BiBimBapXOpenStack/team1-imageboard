package bibimbap.openstack.imageboard.repository.image;

import bibimbap.openstack.imageboard.domain.Image;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class LocalImageRepository implements ImageRepository{

    private static Map<Long, Image> store = new HashMap<>();
    private static Long sequence = 0L;

    private final String uploadDir = "/Users/jaemin/desktop/uploaded/";

    @Override
    public String saveFile(MultipartFile mptFile,String imageName) {
        String filePath = _getFilePath(mptFile, imageName);
        File file = new File(filePath);
        try {
            mptFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }

    private String _getFilePath(MultipartFile mptFile, String imageName) {
        String ogName = mptFile.getOriginalFilename();
        String suffix = ogName.split("\\.")[1];
        StringBuilder sb = new StringBuilder();
        sb.append(uploadDir).append(imageName);
        sb.append(".").append(suffix);
        return sb.toString();
    }

    @Override
    public void saveImage(Image image) {
        image.setId(++sequence);
        store.put(image.getId(), image);
    }

    @Override
    public Image getImageById(Long id) {
        return store.get(id);
    }

    @Override
    public void deleteImage(Long id) {
        store.remove(id);
    }
}
