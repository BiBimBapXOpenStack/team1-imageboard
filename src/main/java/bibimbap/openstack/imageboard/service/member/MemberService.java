package bibimbap.openstack.imageboard.service.member;

import bibimbap.openstack.imageboard.decorator.MemberDecorator;
import bibimbap.openstack.imageboard.domain.member.Member;
import bibimbap.openstack.imageboard.dto.ResultDto;
import bibimbap.openstack.imageboard.dto.member.MemberDto;
import bibimbap.openstack.imageboard.dto.member.MemberLoginDto;
import bibimbap.openstack.imageboard.dto.member.MemberSaveDto;
import bibimbap.openstack.imageboard.repository.member.MemberRepository;
import bibimbap.openstack.imageboard.security.util.SecurityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import static bibimbap.openstack.imageboard.dto.ResultDto.makeResult;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberDecorator memberDecorator;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseEntity<ResultDto> signup(MemberSaveDto memberSaveDto) {
        if (memberRepository.findByEmail(
                memberSaveDto.getEmail()).orElse(null) != null
        ) {
            return makeResult(HttpStatus.CONFLICT, "이미 가입되어 있는 유저입니다.");
        }

        Member member = memberDecorator.save(memberSaveDto);
        return makeResult(HttpStatus.OK, new MemberDto(member));
    }

    @Transactional
    public ResponseEntity<ResultDto> findMe() {
        Member member = SecurityUtil.getCurrentEmail().flatMap(memberRepository::findOneWithAuthoritiesByEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 유저가 없습니다."));

        return makeResult(HttpStatus.OK, new MemberDto(member));
    }
}
