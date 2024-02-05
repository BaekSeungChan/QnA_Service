package com.example.sbb;

import com.example.sbb.answer.Answer;
import com.example.sbb.question.Question;
import com.example.sbb.question.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AnswerRepository {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private com.example.sbb.answer.AnswerRepository answerRepository;

    private static int lastSampleDateId;

    @BeforeEach
    void beforeEach() {
        clearData();
        createSampleData();
    }

    private void clearData() {
        QuestionRepositoryTests.clearData(questionRepository);

        answerRepository.truncateTable();
    }

    private void truncate(){

    }

    private void createSampleData() {
        QuestionRepositoryTests.createSampleData(questionRepository);

        Question q = questionRepository.findById(1).get();

        Answer a1 = new Answer();
        a1.setContent("sbb는 질문답변 게시판입니다.");
        a1.setQuestion(q);
        a1.setCreateDate(LocalDateTime.now());

        answerRepository.save(a1);

        Answer a2= new Answer();
        a2.setContent("sbb에서는 주로 스프링관련 내용을 다룹니다.");
        a2.setQuestion(q);
        a2.setCreateDate(LocalDateTime.now());

        answerRepository.save(a2);
    }

    @Test
    void 저장(){
        Question q = questionRepository.findById(2).get();

        Answer a = new Answer();
        a.setContent("네 자동으로 생성됩니다11.");
        a.setQuestion(q);
        a.setCreateDate(LocalDateTime.now());

        answerRepository.save(a);
    }

    @Test
    void 조회(){
        Answer a = answerRepository.findById(1).get();
        assertThat(a.getContent()).isEqualTo("sbb는 질문답변 게시판입니다.");
    }

}
