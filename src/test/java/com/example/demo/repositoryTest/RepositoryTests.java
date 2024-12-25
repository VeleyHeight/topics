package com.example.demo.repositoryTest;

import com.example.demo.repository.QuestionsRepository;
import com.example.demo.repository.ReactionsRepository;
import com.example.demo.repository.TopicsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

@DataJpaTest
@SpringBootTest
class RepositoryTests {
    @Autowired
    private TopicsRepository topicsRepository;

    @Autowired
    private QuestionsRepository questionsRepository;

    @Autowired
    private ReactionsRepository reactionsRepository;


}
