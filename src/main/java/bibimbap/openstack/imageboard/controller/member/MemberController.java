package bibimbap.openstack.imageboard.controller.member;

import bibimbap.openstack.imageboard.dto.ResultDto;
import bibimbap.openstack.imageboard.dto.member.MemberLoginDto;
import bibimbap.openstack.imageboard.dto.member.MemberSaveDto;
import bibimbap.openstack.imageboard.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("login")
    public ResponseEntity<ResultDto> login(@RequestBody MemberLoginDto memberLoginDto) {
         return memberService.login(memberLoginDto);
    }

    @PostMapping("signup")
    public ResponseEntity<ResultDto> signup(@RequestBody MemberSaveDto memberSaveDto) {
        return memberService.signup(memberSaveDto);
    }
}
