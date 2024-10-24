package yyytir777.persist.domain.log.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yyytir777.persist.domain.common.BaseEntity;
import yyytir777.persist.domain.member.entity.Member;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Log extends BaseEntity {

    @Id
    @Column(name = "log_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Builder.Default
    @Column(name = "view_count")
    private long viewCount = 0L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}