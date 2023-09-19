package appaanjanda.snooping;

import java.time.LocalDateTime;
import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SnoopingApplication {

	public static void main(String[] args) {
		// timezone 설정
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		SpringApplication.run(SnoopingApplication.class, args);

		LocalDateTime now = LocalDateTime.now();
		System.out.println("현재시간 " + now);
	}

}
