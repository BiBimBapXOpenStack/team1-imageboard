package bibimbap.openstack.imageboard.controller.member;

import bibimbap.openstack.imageboard.dto.ResultDto;
import bibimbap.openstack.imageboard.dto.member.MemberLoginDto;
import bibimbap.openstack.imageboard.dto.member.MemberSaveDto;
import bibimbap.openstack.imageboard.service.member.AuthService;
import bibimbap.openstack.imageboard.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/")
    public ResponseEntity<ResultDto> findMe() {
        return memberService.findMe();
    }

    @PostMapping("/signup")
    public ResponseEntity<ResultDto> signup(@RequestBody MemberSaveDto memberSaveDto) {
        return memberService.signup(memberSaveDto);
    }

    // 회원 정보 조회 API

}
