package com.ssafy.trycatch.qna.controller.advice;

import com.ssafy.trycatch.qna.controller.annotation.IncreaseViewCount;
import com.ssafy.trycatch.qna.domain.Question;
import com.ssafy.trycatch.qna.domain.QuestionRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class QuestionViewCount {

    private final QuestionRepository repository;

    @Autowired
    public QuestionViewCount(QuestionRepository repository) {
        this.repository = repository;
    }

    @Pointcut("@annotation(com.ssafy.trycatch.qna.controller.annotation.IncreaseViewCount)")
    private void viewCount() {}

    @Pointcut("execution(com.ssafy.trycatch.qna.domain.Question *.*(..))")
    private void returnQuestion() {}

    @AfterReturning(value = "viewCount() && returnQuestion()", returning = "question")
    public void after(JoinPoint joinPoint, Question question) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        IncreaseViewCount increaseViewCount = method.getAnnotation(IncreaseViewCount.class);
        question.increaseViewCount(increaseViewCount.amount());
        repository.save(question);
    }
}
