package project2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project2.model.Question;
import project2.repository.IQuestionRepository;
import project2.service.IQuestionService;

import java.util.List;

@Service
public class QuestionService implements IQuestionService {
    @Autowired
    private IQuestionRepository questionRepository;

    @Override
    public List<Question> findAllQuestion() {
        return questionRepository.findAll();
    }
}
