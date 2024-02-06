package com.example.sbb;

import com.example.sbb.question.Question;
import com.example.sbb.question.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class QuestionRepositoryTests {

    @Autowired
    private QuestionRepository questionRepository;

    private static int lastSampleDateId;

    @Test
    @BeforeEach
    void beforeEach() {
        clearData();
        createSampleData();
    }



    public static int createSampleData(QuestionRepository questionRepository) {
        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());

        questionRepository.save(q1);

        Question q2 = new Question();
        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalDateTime.now());

        questionRepository.save(q2);  // 두번쨰 질문 저장

        assertThat(q1.getId()).isGreaterThan(0);
        assertThat(q2.getId()).isGreaterThan(q1.getId());

        return q2.getId();

    }

    private void createSampleData(){
        lastSampleDateId = createSampleData(questionRepository);
    }

    public static void clearData(QuestionRepository questionRepository) {
        questionRepository.deleteAll(); // DELETE FROM question;
        questionRepository.truncateTable();
    }

    private void clearData() {
        clearData(questionRepository);
    }



    @Test
    void 저장() {
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
    void 삭제() {
        assertThat(questionRepository.count()).isEqualTo(lastSampleDateId);
        Question q = questionRepository.findById(1).get();
        questionRepository.delete(q);

        assertThat(questionRepository.count()).isEqualTo(lastSampleDateId - 1);
    }


    @Test
    void 수정() {
        Question q = questionRepository.findById(1).get();
        q.setSubject("수정된 제목");
        questionRepository.save(q);

        q = questionRepository.findById(1).get();
        assertThat(q.getSubject()).isEqualTo("수정된 제목");
    }

    @Test
    void findALll() {
        List<Question> all = questionRepository.findAll();
        assertThat(all.size()).isEqualTo(lastSampleDateId);

        Question q = all.get(0);
        assertThat(q.getSubject()).isEqualTo("sbb가 무엇인가요?");
    }

    @Test
    void findBySubject(){
        Question q = questionRepository.findBySubject("sbb가 무엇인가요?");
//        assertEquals(1, q.getId());

        assertThat(q.getId()).isEqualTo(1);
    }

    @Test
    void findBySubjectAndContent() {
        Question q = questionRepository.findBySubjectAndContent("sbb가 무엇인가요?","sbb에 대해서 알고 싶습니다.");
//        assertEquals(1, q.getId());
        assertThat(q.getId()).isEqualTo(1);
    }

    @Test
    void findBySubjectLike(){
        List<Question> qList = questionRepository.findBySubjectLike("sbb%");
        Question q = qList.get(0);
//        assertEquals("sbb가 무엇인가요?", q.getSubject());
        assertThat(q.getSubject()).isEqualTo("sbb가 무엇인가요?");
    }

}