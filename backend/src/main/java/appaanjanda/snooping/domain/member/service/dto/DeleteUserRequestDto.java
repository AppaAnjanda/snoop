package appaanjanda.snooping.domain.member.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DeleteUserRequestDto {

    private String email;

    private String password;
    private String checkPassword;
}
