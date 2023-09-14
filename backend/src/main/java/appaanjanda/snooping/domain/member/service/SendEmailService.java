package appaanjanda.snooping.domain.member.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import appaanjanda.snooping.domain.member.service.dto.SendEmailRequestDto;
import appaanjanda.snooping.domain.member.repository.MemberRepository;
import appaanjanda.snooping.domain.member.service.dto.MailDto;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@Transactional
public class SendEmailService {

	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	private final JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String FROM_ADDRESS;

	public MailDto createMailAndChangePassword(SendEmailRequestDto requestDto) {
		String str = getTempPassword();
		MailDto dto = MailDto.builder()
			.title(requestDto.getName() + "님의 AppaAnjanda 임시비밀번호 안내 이메일 입니다.")
			.message("안녕하세요. AppaAnjanda 임시비밀번호 안내 관련 이메일 입니다. \n\n" + "[" + requestDto.getName() + "]" + "님의 임시 비밀번호는 "
				+ str + " 입니다.")
			.address(requestDto.getEmail())
			.build();

		updatePassword(str, requestDto.getEmail());
		return dto;
	}

	private void updatePassword(String str, String userEmail) {
		String password = bCryptPasswordEncoder.encode(str);
		memberRepository.findMemberByEmail(userEmail).orElseThrow();
		memberRepository.updateMemberPassword(userEmail, password);
	}

	private String getTempPassword() {
		char[] charSet = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
			'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

		String str = "";

		int idx = 0;
		for (int i = 0; i < 10; i++) {
			idx = (int)(charSet.length * Math.random());
			str += charSet[idx];
		}
		return str;
	}

	public String mailSend(MailDto mailDto){
		System.out.println("이멜 전송 완료!");
		SimpleMailMessage message = new SimpleMailMessage();

		message.setTo(mailDto.getAddress());
		message.setSubject(mailDto.getTitle());
		message.setText(mailDto.getMessage());
		message.setFrom(FROM_ADDRESS);
		message.setReplyTo(FROM_ADDRESS);

		mailSender.send(message);

		return "성공";
	}

}

