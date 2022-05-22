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

    public MemberDto(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
    }
}
