package com.example.sbb;

import com.example.sbb.question.Question;
import com.example.sbb.question.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class QuestionApplicationTests {

    @Autowired
    private QuestionRepository questionRepository;

    private static int lastSampleDateId;

    @Test
    @BeforeEach
    void beforeEach(){
        clearData();
        clearSampleData();
    }

    private void clearData(){
        questionRepository.disableForeignKeyChecks();
        questionRepository.truncate();
        questionRepository.enableForeignKeyChecks();
    }

    private void clearSampleData(){
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

        assertThat(q1.getId()).isGreaterThan(0);
        assertThat(q2.getId()).isGreaterThan(q1.getId());

        lastSampleDateId = q2.getId();

    }

    @Test
    void 저장(){
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


        assertThat(q1.getId()).isEqualTo(lastSampleDateId + 1);
        assertThat(q2.getId()).isEqualTo(lastSampleDateId + 2);

    }

    @Test
    void 삭제(){
        assertThat(questionRepository.count()).isEqualTo(lastSampleDateId);
        Question q  = questionRepository.findById(1).get();
        questionRepository.delete(q);

        assertThat(questionRepository.count()).isEqualTo(lastSampleDateId -1);
    }

//    @Test
//    void testJpa(){
//        Question q1 = new Question();
//        q1.setSubject("sbb가 무엇인가요?");
//        q1.setContent("sbb에 대해서 알고 싶습니다.");
//        q1.setCreateDate(LocalDateTime.now());
//
//        this.questionRepository.save(q1); // 첫번째 질문 저장
//
//        System.out.println(q1.getId());
//
//        Question q2 = new Question();
//        q2.setSubject("스프링부트 모델 질문입니다.");
//        q2.setContent("id는 자동으로 생성되나요?");
//        q2.setCreateDate(LocalDateTime.now());
//
//        this.questionRepository.save(q2);  // 두번쨰 질문 저장
//
//        System.out.println(q2.getId());
//
//        questionRepository.disableForeignKeyChecks();
//        questionRepository.truncate();
//        questionRepository.enableForeignKeyChecks();
//
//    }
//

//
////    @Test
////    void testJpa0(){
////        questionRepository.truncate();
////    }
//
//    @Test
//    void testJpa2(){
//        // SELECT * FROM question
//        List<Question> all  = questionRepository.findAll();
//        assertEquals(2, all.size());
//
//        Question q = all.get(0);
//        assertEquals("sbb가 무엇인가요?", q.getSubject());
//    }
//
//    @Test
//    void testJpa3(){
//        Question q = questionRepository.findByContent("id는 자동으로 생성되나요?");
//
//
//        assertEquals(q.getContent(), "id는 자동으로 생성되나요?");
//    }
//
//
//    @Test
//    void testJpa4(){
//        Question q = questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
//
//
//        assertEquals(1, q.getId());
//    }
//
//    @Test
//    void testJpa5(){
//        List<Question> qList =  questionRepository.findBySubjectLike("sbb%");
//
//        Question q = qList.get(0);
//
//        assertEquals("sbb가 무엇인가요?", q.getSubject());
//
//    }
//
//    @Test
//    void testJpa6(){
//        Optional<Question> oq  = questionRepository.findById(1);
//        assertThat(oq.isPresent());
//        Question q =oq.get();
//        q.setSubject("수정된 제목");
//        questionRepository.save(q);
//
//    }
//


}
