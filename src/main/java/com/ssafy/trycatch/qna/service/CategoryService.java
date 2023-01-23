package com.ssafy.trycatch.qna.service;

import com.ssafy.trycatch.qna.domain.Category;
import com.ssafy.trycatch.qna.domain.CategoryRepository;
import com.ssafy.trycatch.qna.service.exceptions.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
    }

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
}
