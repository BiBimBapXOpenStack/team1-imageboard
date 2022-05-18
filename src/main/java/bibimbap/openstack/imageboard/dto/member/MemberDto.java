package bibimbap.openstack.imageboard.dto.member;

import bibimbap.openstack.imageboard.domain.member.Member;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Getter
public class MemberDto {
    private final Long id;
    private final String username;
    private final String email;
    private final String password;
    private final Date createdAt;
    private final Date updatedAt;

    public MemberDto(Member member) {
        this.id = member.getId();
        this.createdAt = member.getCreatedAt();
        this.updatedAt = member.getUpdatedAt();
        this.username = member.getUsername();
        this.email = member.getEmail();
        this.password = member.getPassword();
    }
}
