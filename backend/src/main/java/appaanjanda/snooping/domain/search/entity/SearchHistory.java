package appaanjanda.snooping.domain.search.entity;

import appaanjanda.snooping.domain.member.entity.Member;
import appaanjanda.snooping.global.common.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

// 검색 기록
@Entity
@Setter
@Getter
@NoArgsConstructor
public class SearchHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "search_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private String keyword;

}
