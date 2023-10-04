package appaanjanda.snooping.domain.firebase.entity;

import appaanjanda.snooping.domain.member.entity.Member;
import appaanjanda.snooping.global.common.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class AlertHistory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String body;

    private String imageUrl;

    private String productCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonIgnore
    private Member member;

    @Builder
    public AlertHistory(Long id, String title, String body, String imageUrl, String productCode, Member member) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.member = member;
        this.imageUrl = imageUrl;
        this.productCode = productCode;
    }
}
