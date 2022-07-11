package bibimbap.openstack.imageboard.decorator;

import bibimbap.openstack.imageboard.domain.member.Authority;
import bibimbap.openstack.imageboard.domain.member.Member;
import bibimbap.openstack.imageboard.dto.member.MemberSaveDto;
import bibimbap.openstack.imageboard.model.MemberRole;
import bibimbap.openstack.imageboard.repository.member.AuthorityRepository;
import bibimbap.openstack.imageboard.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class MemberDecorator {
    private final MemberRepository memberRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Member save(MemberSaveDto memberSaveDto) {
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        if (authorityRepository.findByAuthorityName(MemberRole.ROLE_USER.name()).isEmpty()) {
            authorityRepository.save(authority);
        }

        memberSaveDto.setPassword(passwordEncoder.encode(memberSaveDto.getPassword()));

        return memberRepository.save(memberSaveDto.toEntity(Collections.singleton(authority)));
    }

}
