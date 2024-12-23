package yyytir777.persist.domain.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yyytir777.persist.domain.category.CategoryConverter;
import yyytir777.persist.domain.category.dto.CategoryCreateRequestDto;
import yyytir777.persist.domain.category.dto.CategoryResponseDto;
import yyytir777.persist.domain.category.dto.CategoryUpdateRequestDto;
import yyytir777.persist.domain.category.entity.Category;
import yyytir777.persist.domain.category.repository.CategoryRepository;
import yyytir777.persist.domain.log.entity.Log;
import yyytir777.persist.domain.log.repository.LogRepository;
import yyytir777.persist.domain.member.entity.Member;
import yyytir777.persist.domain.member.repository.MemberRepository;
import yyytir777.persist.global.error.ErrorCode;
import yyytir777.persist.global.error.exception.CategoryException;
import yyytir777.persist.global.error.exception.MemberException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;
    private final LogRepository logRepository;

    private final String defaultCategoryName = "Default";

    public Category saveCategory(CategoryCreateRequestDto categoryCreateRequestDto, String memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new MemberException(ErrorCode.MEMBER_NOT_EXIST));

        Optional<Category> findCategory = categoryRepository.findByName(categoryCreateRequestDto.getName());
        if(findCategory.isPresent()) throw new CategoryException(ErrorCode.CATEGORY_EXIST);

        Category category = CategoryConverter.CreateRequestToEntity(categoryCreateRequestDto, member);

        categoryRepository.save(category);
        return category;
    }

    public List<CategoryResponseDto> getAllCategory(String memberId) {
        memberRepository.findById(memberId).orElseThrow(() ->
                new MemberException(ErrorCode.MEMBER_NOT_EXIST));

        return categoryRepository.findAllByMemberId(memberId).stream()
                .map(CategoryResponseDto::of)
                .toList();
    }

    public Category updateCategory(String memberId, String categoryId, CategoryUpdateRequestDto categoryUpdateRequestDto) {
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new MemberException(ErrorCode.MEMBER_NOT_EXIST));

        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new CategoryException(ErrorCode.CATEGORY_NOT_EXIST));

        if (!validate(category, member)) {
            throw new CategoryException(ErrorCode.NO_AUTHORITY);
        }

        Category updatedCategory = category.updateName(categoryUpdateRequestDto.getName());

        categoryRepository.save(updatedCategory);
        return updatedCategory;
    }

    // 지울 카테고리에 존재하는 로그들을 기본 카테고리로 옮긴 후 카테고리 삭제
    public void deleteCategory(String memberId, String categoryId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new MemberException(ErrorCode.MEMBER_NOT_EXIST));

        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new CategoryException(ErrorCode.CATEGORY_NOT_EXIST));

        if (!validate(category, member)) {
            throw new CategoryException(ErrorCode.NO_AUTHORITY);
        }

        Category noneCategory = categoryRepository.findByName(defaultCategoryName).orElseThrow(() ->
                new CategoryException(ErrorCode.CATEGORY_NOT_EXIST));

        List<Log> logList = logRepository.findAllByCategoryId(category.getId()).stream()
                .map(log -> log.updateCategory(noneCategory)).toList();

        logRepository.saveAll(logList);
        categoryRepository.delete(category);
    }

    private boolean validate(Category category, Member member) {
        return category.getMember().equals(member);
    }
}
