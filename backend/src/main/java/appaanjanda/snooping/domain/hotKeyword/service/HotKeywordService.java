package appaanjanda.snooping.domain.hotKeyword.service;

import appaanjanda.snooping.domain.hotKeyword.entity.HotKeyword;
import appaanjanda.snooping.domain.hotKeyword.repository.HotKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class HotKeywordService {

    private final HotKeywordRepository hotKeywordRepository;

    // 검색어 검색횟수 정렬
    public List<String> getHotKeyword() {

        List<HotKeyword> hotKeywords = hotKeywordRepository.sortAllByCount(Pageable.ofSize(10));

        List<String> result = new ArrayList<>();

        for (HotKeyword hotKeyword:hotKeywords) {
            LocalDateTime createTime = hotKeyword.getCreateTime();
            LocalDateTime now = LocalDateTime.now();
            Duration duration = Duration.between(createTime, now);

            // 일주일 지난 검색어 삭제
            if (duration.toDays() > 7) deleteKeyword(hotKeyword);
            // 검색어 추출
            result.add(hotKeyword.getKeyword());
        }
        return result;
    }

    @Transactional
    // 검색어 삭제
    public void deleteKeyword(HotKeyword hotKeyword) {
        hotKeywordRepository.delete(hotKeyword);
    }

    // 검색 횟수 생성 or 증가
    @Transactional
    public void updateHotKeyword(String keyword) {

        Optional<HotKeyword> hotKeyword = hotKeywordRepository.findByKeyword(keyword);
        // 검색어 있으면 count +1
        if (hotKeyword.isPresent()) {
            HotKeyword existHotKeyword = hotKeyword.get();
            existHotKeyword.setCount(existHotKeyword.getCount() + 1);

        } else {// 없으면 새로 생성
            HotKeyword newHotKeyword = new HotKeyword(keyword, 1);
            hotKeywordRepository.save(newHotKeyword);
        }
    }
}
