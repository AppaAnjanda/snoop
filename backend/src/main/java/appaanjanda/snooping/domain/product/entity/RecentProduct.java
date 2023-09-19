package appaanjanda.snooping.domain.product.entity;

import appaanjanda.snooping.domain.member.entity.Member;
import appaanjanda.snooping.global.common.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

// 최근 본 상품
@Entity
@Getter
@Setter
@NoArgsConstructor
public class RecentProduct extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recent_id")
    private Long id;

    private String productId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

}