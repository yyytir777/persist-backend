package yyytir777.persist.domain.category.controller;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CategoryDto {
    Long categoryId;
    String categoryName;
    List<String> logNames;
}
