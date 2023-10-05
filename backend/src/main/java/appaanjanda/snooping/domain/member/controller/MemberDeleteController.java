package appaanjanda.snooping.domain.member.controller;

import appaanjanda.snooping.domain.member.service.MemberService;
import appaanjanda.snooping.domain.member.service.dto.DeleteUserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class MemberDeleteController {

    private final MemberService memberService;

    @GetMapping("/deleteUserPage")
    public String deleteUserPage() {
        return "deleteUser";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestBody DeleteUserRequestDto request, Model model) {
        memberService.deleteUser(request);
        return "deleteUser";
    }
}
