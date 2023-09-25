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

    private List<SearchContentDto> contents;
    private int currentPage;
    private int totalPage;

    public SearchResponseDto(List<SearchContentDto> searchContentDto, int currentPage, int totalPage) {
        this.contents = searchContentDto;
        this.currentPage = currentPage;
        this.totalPage = totalPage;
    }
}
