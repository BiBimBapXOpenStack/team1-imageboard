package bibimbap.openstack.imageboard.dto.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

public class MemberLoginDto {
    @Schema(description = "이메일")
    private String email;   

    @Schema(description = "비밀번호")
    private String password;

    @Builder
    public MemberLoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
