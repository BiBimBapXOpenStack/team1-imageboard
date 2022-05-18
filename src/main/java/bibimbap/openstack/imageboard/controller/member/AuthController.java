package bibimbap.openstack.imageboard.controller.member;

import bibimbap.openstack.imageboard.dto.ResultDto;
import bibimbap.openstack.imageboard.dto.member.MemberLoginDto;
import bibimbap.openstack.imageboard.service.member.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * JWT 발급 API
     * @param memberLoginDto
     * @return
     */
    @PostMapping("authenticate")
    public ResponseEntity<ResultDto> createJwt(@RequestBody MemberLoginDto memberLoginDto) {
        return authService.authorize(memberLoginDto);
    }
}
