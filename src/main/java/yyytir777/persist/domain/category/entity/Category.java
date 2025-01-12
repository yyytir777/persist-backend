package yyytir777.persist.domain.category.entity;

import jakarta.persistence.*;
import lombok.*;
import yyytir777.persist.domain.common.BaseEntity;
import yyytir777.persist.domain.log.entity.Log;
import yyytir777.persist.domain.member.entity.Member;

import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseEntity {

    @Id
    @Column(name = "category_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Builder.Default
    @Column(name = "name", nullable = false, unique = true)
    private String name = "None";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Log> logList;

    public Category(Member member) {
        this.member = member;
    }

    public Category updateName(String name) {
        this.name = name;
        return this;
    }
}