package bibimbap.openstack.imageboard.util.file;

import bibimbap.openstack.imageboard.domain.image.Image;
import bibimbap.openstack.imageboard.dto.image.ImageUploadDto;
import bibimbap.openstack.imageboard.util.file.auth.AuthManager;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.JSONException;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.Arrays;

@Primary
@Component
@RequiredArgsConstructor
public class ObjectStorageFileManager implements FileManager {

    private final AuthManager authManager;
    private RestTemplate restTemplate;

    public void authentication() throws JSONException, ParseException {
        authManager.generateToken();
    }

    @Override
    public Image save(ImageUploadDto dto) throws JSONException, ParseException, IOException {
        authentication();
        String tokenId = authManager.getToken().getId();

        InputStream inputStream = dto.getFile().getInputStream();


        final RequestCallback requestCallback = new RequestCallback() {
            @Override
            public void doWithRequest(ClientHttpRequest request) throws IOException {
                request.getHeaders().add("X-Auth-Token", tokenId);
                IOUtils.copy(inputStream, request.getBody());
            }
        };

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setBufferRequestBody(false);
        restTemplate = new RestTemplate(requestFactory);

        HttpMessageConverterExtractor<String> responseExtractor = new HttpMessageConverterExtractor<>(String.class, restTemplate.getMessageConverters());

        String URL = "https://api-storage.cloud.toast.com/v1/";
        String storageId = "AUTH_35682dae0076479ab712dbb328468535";
        String containerName = "ajm-test";
        String imageName = dto.getImageName();

        StringBuilder sb = new StringBuilder();
        sb.append(URL).append(storageId).append("/").append(containerName).append("/").append(imageName);
        URL = sb.toString();

        restTemplate.execute(URL, HttpMethod.PUT, requestCallback, responseExtractor);

        return Image.builder()
                .imageName(imageName)
                .imageURL(URL)
                .build();
    }

    @Override
    public byte[] loadFile(String url) throws JSONException, ParseException {
        return _getByteArray(url);
    }

    private byte[] _getByteArray(String url) throws JSONException, ParseException {
        authentication();
        String tokenId = authManager.getToken().getId();

        restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", tokenId);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));

        HttpEntity<String> requestHttpEntity = new HttpEntity<String>(null, headers);

        // API 호출, 데이터를 바이트 배열로 받음
        ResponseEntity<byte[]> response
                = this.restTemplate.exchange(url, HttpMethod.GET, requestHttpEntity, byte[].class);
        return response.getBody();
    }

    @Override
    public Resource loadResource(String url) throws JSONException, ParseException {
        return new ByteArrayResource(_getByteArray(url));
    }

    @Override
    public void deleteImage(String url) throws JSONException, ParseException {
        authentication();
        String tokenId = authManager.getToken().getId();

        restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X_Auth-Token", tokenId);
        HttpEntity<String> requestHttpEntity = new HttpEntity<String>(null, headers);

        restTemplate.exchange(url, HttpMethod.DELETE, requestHttpEntity, String.class);
    }

    @Override
    public String probeContentType(String url) throws IOException, JSONException, ParseException {
        byte[] bytes = _getByteArray(url);
        InputStream is = new ByteArrayInputStream(bytes);
        return URLConnection.guessContentTypeFromStream(is);
    }
}

