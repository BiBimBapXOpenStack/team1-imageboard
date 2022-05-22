package bibimbap.openstack.imageboard.dto;

import bibimbap.openstack.imageboard.security.JwtFilter;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.nio.charset.StandardCharsets;

@Data
public class ResultDto {
    private HttpStatus status;
    private Object result;

    public ResultDto() {
        this.status = HttpStatus.BAD_REQUEST;
        this.result = null;
    }

    public static ResponseEntity<ResultDto> makeResult(HttpStatus status, Object object) {
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        ResultDto message = new ResultDto();
        message.setStatus(status);
        message.setResult(object);

        return new ResponseEntity<>(message, headers, status);
    }

    public static ResponseEntity<ResultDto> makeResult(HttpStatus status, Object object, String jwt) {
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        headers.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        ResultDto message = new ResultDto();
        message.setStatus(status);
        message.setResult(object);

        return new ResponseEntity<>(message, headers, status);
    }

}
