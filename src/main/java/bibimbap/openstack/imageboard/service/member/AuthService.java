package bibimbap.openstack.imageboard.service.member;

import bibimbap.openstack.imageboard.dto.ResultDto;
import bibimbap.openstack.imageboard.dto.member.MemberLoginDto;
import bibimbap.openstack.imageboard.dto.member.TokenDto;
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
import org.springframework.stereotype.Service;

import static bibimbap.openstack.imageboard.dto.ResultDto.makeResult;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public ResponseEntity<ResultDto> authorize(MemberLoginDto memberLoginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(memberLoginDto.getEmail(), memberLoginDto.getPassword());

        System.out.println("--1---");

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        System.out.println("--2--");

        String jwt = tokenProvider.creatToken(authentication);

        System.out.println("--3--");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        System.out.println("--4--");

        return makeResult(HttpStatus.OK, new TokenDto(jwt));
    }

}
