package appaanjanda.snooping.domain.wishbox.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RemoveWishboxResponseDto {
    private Long removeId;

    @Builder
    public RemoveWishboxResponseDto(Long removeId) {
        this.removeId = removeId;
    }
}
