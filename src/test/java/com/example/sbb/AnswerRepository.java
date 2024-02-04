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
        clearSampleData();
    }

    private void clearData() {
        questionRepository.disableForeignKeyChecks();
        answerRepository.truncate();
        questionRepository.enableForeignKeyChecks();
    }

    private void clearSampleData() {
        // question을 만든다.
        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());

        questionRepository.save(q1);

        Question q2 = new Question();
        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalDateTime.now());

        this.questionRepository.save(q2);  // 두번쨰 질문 저장

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
