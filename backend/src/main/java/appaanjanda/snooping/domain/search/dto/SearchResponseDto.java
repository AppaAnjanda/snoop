package appaanjanda.snooping.domain.search.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SearchResponseDto {

    private List<?> contents;
    private int currentPage;
    private int totalPage;

    public SearchResponseDto(List<?> contents, int currentPage, int totalPage) {
        this.contents = contents;
        this.currentPage = currentPage;
        this.totalPage = totalPage;
    }
}
