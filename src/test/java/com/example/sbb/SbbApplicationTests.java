package com.example.sbb;

import com.example.sbb.question.Question;
import com.example.sbb.question.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SbbApplicationTests {
    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void testJpa(){
        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());

        this.questionRepository.save(q1); // 첫번째 질문 저장

        System.out.println(q1.getId());

        Question q2 = new Question();
        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalDateTime.now());

        this.questionRepository.save(q2);  // 두번쨰 질문 저장

        System.out.println(q2.getId());

        questionRepository.disableForeignKeyChecks();
        questionRepository.truncate();
        questionRepository.enableForeignKeyChecks();

    }

    @Test
    void testJpa1(){
        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());

        this.questionRepository.save(q1); // 첫번째 질문 저장

        System.out.println(q1.getId());

        Question q2 = new Question();
        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalDateTime.now());

        this.questionRepository.save(q2);  // 두번쨰 질문 저장

        System.out.println(q2.getId());


        assertThat(q1.getId()).isGreaterThan(0);
        assertThat(q2.getId()).isGreaterThan(q1.getId());

    }

//    @Test
//    void testJpa0(){
//        questionRepository.truncate();
//    }

    @Test
    void testJpa2(){
        // SELECT * FROM question
        List<Question> all  = questionRepository.findAll();
        assertEquals(2, all.size());

        Question q = all.get(0);
        assertEquals("sbb가 무엇인가요?", q.getSubject());
    }

    @Test
    void testJpa3(){
        Question q = questionRepository.findByContent("id는 자동으로 생성되나요?");


        assertEquals(q.getContent(), "id는 자동으로 생성되나요?");
    }


    @Test
    void testJpa4(){
        Question q = questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");


        assertEquals(1, q.getId());
    }

    @Test
    void testJpa5(){
        List<Question> qList =  questionRepository.findBySubjectLike("sbb%");

        Question q = qList.get(0);

        assertEquals("sbb가 무엇인가요?", q.getSubject());

    }

    @Test
    void testJpa6(){
        Optional<Question> oq  = questionRepository.findById(1);
        assertThat(oq.isPresent());
        Question q =oq.get();
        q.setSubject("수정된 제목");
        questionRepository.save(q);

    }

    @Test
    void testJpa7(){
        assertEquals(2, questionRepository.count());
        Optional<Question> oq  = questionRepository.findById(1);
        assertThat(oq.isPresent());
        Question q = oq.get();
        questionRepository.delete(q);
        assertEquals(1, questionRepository.count());
    }

}
