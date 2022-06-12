package bibimbap.openstack.imageboard.util.file.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data @Builder
public class Token {
    private String id;
    private Date expires;
    private String tenantId;

}
