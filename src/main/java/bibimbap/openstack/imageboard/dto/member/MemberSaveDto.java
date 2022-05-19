package bibimbap.openstack.imageboard.dto.member;

import bibimbap.openstack.imageboard.domain.member.Authority;
import bibimbap.openstack.imageboard.domain.member.Member;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class MemberSaveDto {

    private String email;
    private String password;
    private String username;

    public Member toEntity(Set<Authority> authorities) {
        return Member.builder()
                .email(email)
                .password(password)
                .username(username)
                .authorities(authorities)
                .build();
    }
}
