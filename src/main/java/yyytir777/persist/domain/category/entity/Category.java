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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Column(name = "name", nullable = false)
    private String name = "None";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Log> logList;

    public Category(Long categoryId, Member member) {
        this.id = categoryId;
        this.member = member;
        this.name = "demo";
    }

    public Category(Long categoryId, Member member, String name) {
        this.id = categoryId;
        this.member = member;
        this.name = name;
    }

    public Category updateName(String name) {
        this.name = name;
        return this;
    }

    public Category(Member member) {
        this.member = member;
        this.name = "demo";
    }
}