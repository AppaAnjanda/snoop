package appaanjanda.snooping.domain.hotKeyword.entity;


import appaanjanda.snooping.global.common.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class HotKeyword extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hotKeyword_id")
    private Long id;

    private String keyword;

    private int count;

    public HotKeyword(String keyword, int count) {
        this.keyword = keyword;
        this.count = count;
    }
}
