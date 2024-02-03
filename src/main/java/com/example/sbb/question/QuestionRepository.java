package com.example.sbb.question;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    Question findBySubject(String subject);

    Question findByContent(String content);

    Question findBySubjectAndContent(String subject, String content);
}
