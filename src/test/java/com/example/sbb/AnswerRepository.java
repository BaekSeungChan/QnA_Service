package com.example.sbb;

import com.example.sbb.answer.Answer;
import com.example.sbb.question.Question;
import com.example.sbb.question.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class AnswerRepository {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private com.example.sbb.answer.AnswerRepository answerRepository;

    private static int lastSampleDateId;

    @Test
    @BeforeEach
    void beforeEach() {
        clearData();
        createSampleData();
    }

    private void clearData() {
        QuestionRepositoryTests.clearData(questionRepository);
        questionRepository.disableForeignKeyChecks();
        answerRepository.truncate();
        questionRepository.enableForeignKeyChecks();
    }

    private void createSampleData() {
        QuestionRepositoryTests.createSampleData(questionRepository);
    }

    @Test
    void 저장(){
        Question q = questionRepository.findById(2).get();

        Answer a = new Answer();
        a.setContent("네 자동으로 생성됩니다.");
        a.setQuestion(q);
        a.setCreateDate(LocalDateTime.now());

        answerRepository.save(a);
    }

}
