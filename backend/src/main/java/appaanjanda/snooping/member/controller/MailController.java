package appaanjanda.snooping.member.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import appaanjanda.snooping.member.service.MemberService;
import appaanjanda.snooping.member.service.SendEmailService;
import appaanjanda.snooping.member.service.dto.ChangePasswordRequestDto;
import appaanjanda.snooping.member.service.dto.MailDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class MailController {

	private final SendEmailService sendEmailService;


	//등록된 이메일로 임시비밀번호를 발송하고 발송된 임시비밀번호로 사용자의 pw를 변경하는 컨트롤러
	@PostMapping("/check/findPw/sendEmail")
	public @ResponseBody MailDto sendEmail(@RequestBody ChangePasswordRequestDto requestDto){
		MailDto dto = sendEmailService.createMailAndChangePassword(requestDto);
		sendEmailService.mailSend(dto);

		return dto;

	}
}
