package bibimbap.openstack.imageboard.util.file;

import bibimbap.openstack.imageboard.domain.image.Image;
import bibimbap.openstack.imageboard.dto.image.ImageUploadDto;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

public interface FileManager {
    Image save(ImageUploadDto dto);
    File loadFile(String url);
    Resource loadResource(String url);
    String probeContentType(String url) throws IOException;
}
