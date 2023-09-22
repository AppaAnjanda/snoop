package appaanjanda.snooping.domain.member.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import appaanjanda.snooping.domain.member.service.SendEmailService;
import appaanjanda.snooping.domain.member.service.dto.SendEmailRequestDto;
import appaanjanda.snooping.domain.member.service.dto.MailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MailController {

	private final SendEmailService sendEmailService;


	//등록된 이메일로 임시비밀번호를 발송하고 발송된 임시비밀번호로 사용자의 pw를 변경하는 컨트롤러
	@PostMapping("/check/sendEmail")
	public @ResponseBody MailDto sendEmail(@RequestBody SendEmailRequestDto requestDto){
		MailDto dto = sendEmailService.createMailAndChangePassword(requestDto);
		sendEmailService.mailSend(dto);
		return dto;

	}
}
