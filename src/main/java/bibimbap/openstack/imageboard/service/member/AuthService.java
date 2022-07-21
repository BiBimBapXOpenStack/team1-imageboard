package bibimbap.openstack.imageboard.service.member;

import bibimbap.openstack.imageboard.domain.member.Member;
import bibimbap.openstack.imageboard.dto.ResultDto;
import bibimbap.openstack.imageboard.dto.member.MemberDto;
import bibimbap.openstack.imageboard.dto.member.MemberLoginDto;
import bibimbap.openstack.imageboard.repository.member.MemberRepository;
import bibimbap.openstack.imageboard.security.TokenProvider;
import bibimbap.openstack.imageboard.util.log.CustomLogger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import static bibimbap.openstack.imageboard.dto.ResultDto.makeResult;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public ResponseEntity<ResultDto> authorize(HttpServletRequest request, MemberLoginDto memberLoginDto) {
        Member member = memberRepository.findOneWithAuthoritiesByEmail(memberLoginDto.getEmail()).orElse(null);

        if (member == null) {
            CustomLogger.log(request,"MEMBER/LOGIN FAILED : 존재하지않는 email (email:"+memberLoginDto.getEmail()+")");
            return makeResult(HttpStatus.BAD_REQUEST, "없는 계정입니다.");
        }
        if (!passwordEncoder.matches(memberLoginDto.getPassword(), member.getPassword())) {
            CustomLogger.log(request,"MEMBER/LOGIN FAILED : 비밀번호가 일치하지 않습니다 (email:"+memberLoginDto.getEmail()+")");
            return makeResult(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(memberLoginDto.getEmail(), memberLoginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.creatToken(authentication);

        CustomLogger.log(request,"MEMBER/LOGIN SUCCESS (email:"+memberLoginDto.getEmail()+")");
        return makeResult(HttpStatus.OK, new MemberDto(member), jwt);
    }

}
