package com.example.sbb.answer;

import com.example.sbb.base.RepositoryUtil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AnswerRepository extends JpaRepository<Answer, Integer>, RepositoryUtil {

    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE answer AUTO_INCREMENT = 1", nativeQuery = true)
    void truncate(); // 이거 지우면 안됨. truncateTable 하면 자동으로 이게 실행됨
}
