package bibimbap.openstack.imageboard.util.file;

import bibimbap.openstack.imageboard.domain.image.Image;
import bibimbap.openstack.imageboard.dto.image.ImageUploadDto;

import org.json.JSONException;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public interface FileManager {
    Image save(ImageUploadDto dto) throws JSONException, ParseException, IOException;
    byte[] loadFile(String url) throws JSONException, ParseException, IOException;
    Resource loadResource(String url) throws JSONException, ParseException;
    String probeContentType(String url) throws IOException, JSONException, ParseException;

    void deleteImage(String url) throws JSONException, ParseException;
}
