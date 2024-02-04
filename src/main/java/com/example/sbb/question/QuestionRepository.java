package com.example.sbb.question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    Question findBySubject(String subject);

    Question findByContent(String content);

    Question findBySubjectAndContent(String subject, String content);

   List<Question> findBySubjectLike(String subject);

   @Modifying
   @Transactional
   @Query(value = "TRUNCATE TABLE question", nativeQuery = true)
    void truncate();


    @Modifying
    @Transactional
    @Query(value = "SET FOREIGN_KEY_CHECKS = 0", nativeQuery = true)

   void disableForeignKeyChecks();
    @Modifying
    @Transactional
    @Query(value = "SET FOREIGN_KEY_CHECKS = 1", nativeQuery = true)
   void enableForeignKeyChecks();
}
