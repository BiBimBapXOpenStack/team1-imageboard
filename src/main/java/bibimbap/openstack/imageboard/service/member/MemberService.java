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
import org.springframework.stereotype.Service;

import static bibimbap.openstack.imageboard.dto.ResultDto.makeResult;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberDecorator memberDecorator;

    public ResponseEntity<ResultDto> signup(MemberSaveDto memberSaveDto) {
        if (memberRepository.findByEmail(
                memberSaveDto.getEmail()).orElse(null) != null
        ) {
            return makeResult(HttpStatus.INTERNAL_SERVER_ERROR, "이미 가입되어 있는 유저입니다.");
        }

        Member member = memberDecorator.save(memberSaveDto);
        return makeResult(HttpStatus.OK, new MemberDto(member));
    }
}
