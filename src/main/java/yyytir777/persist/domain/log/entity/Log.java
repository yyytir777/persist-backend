package yyytir777.persist.domain.log.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yyytir777.persist.domain.category.entity.Category;
import yyytir777.persist.domain.common.BaseEntity;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Log extends BaseEntity {

    @Id
    @Column(name = "log_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "thumbnail", length = 1000)
    private String thumbnail;

    @Lob
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "preview")
    private String preview;

    @Builder.Default
    @Column(name = "view_count")
    private long viewCount = 0L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public Log(Long logId, Category category) {
        this.id = logId;
        this.category = category;
        this.title = "demo";
        this.content = "demo";
    }

    public Log updateCategory(Category category) {
        this.category = category;
        return this;
    }
}