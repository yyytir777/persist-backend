package yyytir777.persist.domain.member.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yyytir777.persist.domain.category.entity.Category;
import yyytir777.persist.domain.common.BaseEntity;
import yyytir777.persist.domain.common.Role;
import yyytir777.persist.domain.common.Type;

import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {

    @Id
    @Column(name = "member_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "log_name")
    private String memberLogName;

    @Builder.Default
    @Column(name = "thumbnail")
    private String thumbnail = "Default";

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Type type;

    @Lob
    @Column(name = "readme", columnDefinition = "TEXT")
    private String readme;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Category> categoryList;

    public Member(Long id) {
        this.id = id;
        this.email = "test@test.com";
        this.name = "demo";
        this.memberLogName = "demo";
        this.readme = "demo";
        this.thumbnail = "demo";
        this.role = Role.USER;
        this.type = Type.KAKAO;
    }
}