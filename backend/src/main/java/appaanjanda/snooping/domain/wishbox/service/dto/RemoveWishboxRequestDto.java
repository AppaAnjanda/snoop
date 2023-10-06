package appaanjanda.snooping.domain.wishbox.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class RemoveWishboxRequestDto {
    private List<Long> wishboxIds;

    @Builder
    public RemoveWishboxRequestDto(List<Long> wishboxIds) {
        this.wishboxIds = wishboxIds;
    }
}
