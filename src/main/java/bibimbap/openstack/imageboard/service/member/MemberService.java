package bibimbap.openstack.imageboard.service.member;

import bibimbap.openstack.imageboard.decorator.MemberDecorator;
import bibimbap.openstack.imageboard.domain.member.Member;
import bibimbap.openstack.imageboard.dto.ResultDto;
import bibimbap.openstack.imageboard.dto.member.MemberDto;
import bibimbap.openstack.imageboard.dto.member.MemberLoginDto;
import bibimbap.openstack.imageboard.dto.member.MemberSaveDto;
import bibimbap.openstack.imageboard.repository.member.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            return makeResult(HttpStatus.INTERNAL_SERVER_ERROR, "이미 가입되어 있는 유저입니다.");
        }

        Member member = memberDecorator.save(memberSaveDto);
        return makeResult(HttpStatus.OK, new MemberDto(member));
    }

    @Transactional
    public ResponseEntity<ResultDto> login(MemberLoginDto memberLoginDto) {
        Member member = memberRepository.findOneWithAuthoritiesByEmail(memberLoginDto.getEmail()).orElse(null);

        if (member == null)
            return makeResult(HttpStatus.BAD_REQUEST, "없는 계정입니다.");
        if (passwordEncoder.matches(memberLoginDto.getPassword(), member.getPassword()))
            return makeResult(HttpStatus.OK, new MemberDto(member));

        return makeResult(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
    }
}
