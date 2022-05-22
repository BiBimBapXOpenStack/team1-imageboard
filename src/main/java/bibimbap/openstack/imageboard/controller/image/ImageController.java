package bibimbap.openstack.imageboard.controller.image;

import bibimbap.openstack.imageboard.domain.image.Image;
import bibimbap.openstack.imageboard.dto.image.ImageUploadDto;
import bibimbap.openstack.imageboard.service.image.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@RequiredArgsConstructor
@RestController
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
@RequestMapping("/api/images")
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/")
    public ResponseEntity uploadImage(@ModelAttribute ImageUploadDto dto) {
        imageService.saveImage(dto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> showImage(@PathVariable Long id) {
        if (!imageService.isExistImage(id)) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        Image image = imageService.getImageById(id);

        String contentType = imageService.getContentType(image.getImageURL());
        Resource resource = imageService.getResource(image.getImageURL());

        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", contentType);

        return new ResponseEntity<>(resource,header,HttpStatus.OK);
    }

    @GetMapping("/meta/{id}")
    public ResponseEntity<Image> getImageMeta(@PathVariable Long id) {
        if (!imageService.isExistImage(id)) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        Image result = imageService.getImageById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/download/{id}")
    public void downloadImage(@PathVariable Long id, HttpServletResponse response) throws IOException {

        Image image = imageService.getImageById(id);

        File downloadFile = imageService.getFile(image.getImageURL());

        byte[] fileByte = Files.readAllBytes(downloadFile.toPath());
        response.setContentType("application/octet-stream");
        response.setContentLength(fileByte.length);

        response.setHeader("Content-Disposition", "attachment; fileName=\"" + image.getImageName() + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");

        response.getOutputStream().write(fileByte);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteImage(@PathVariable Long id) {
        if (!imageService.isExistImage(id)) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        imageService.deleteImageById(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
