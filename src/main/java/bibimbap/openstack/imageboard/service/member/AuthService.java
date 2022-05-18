package bibimbap.openstack.imageboard.service.member;

import bibimbap.openstack.imageboard.domain.member.Member;
import bibimbap.openstack.imageboard.dto.ResultDto;
import bibimbap.openstack.imageboard.dto.member.MemberDto;
import bibimbap.openstack.imageboard.dto.member.MemberLoginDto;
import bibimbap.openstack.imageboard.dto.member.TokenDto;
import bibimbap.openstack.imageboard.repository.member.MemberRepository;
import bibimbap.openstack.imageboard.security.JwtFilter;
import bibimbap.openstack.imageboard.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static bibimbap.openstack.imageboard.dto.ResultDto.makeResult;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public ResponseEntity<ResultDto> authorize(MemberLoginDto memberLoginDto) {
        Member member = memberRepository.findOneWithAuthoritiesByEmail(memberLoginDto.getEmail()).orElse(null);

        if (member == null)
            return makeResult(HttpStatus.BAD_REQUEST, "없는 계정입니다.");
        if (!passwordEncoder.matches(memberLoginDto.getPassword(), member.getPassword()))
            return makeResult(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(memberLoginDto.getEmail(), memberLoginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.creatToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return makeResult(HttpStatus.OK, new TokenDto(jwt));
    }

}
