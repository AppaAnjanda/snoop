package appaanjanda.snooping.domain.recentProduct.entity;

import appaanjanda.snooping.domain.member.entity.Member;
import appaanjanda.snooping.global.common.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    private String productCode;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public void updateTime(){
        this.updateTime = LocalDateTime.now();
    }
}
