package project2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project2.model.Question;
import project2.model.Topic;
import project2.repository.ITopicRepository;
import project2.service.IQuestionService;
import project2.service.ITopicService;

import java.util.List;

@RestController
@RequestMapping("/manager/api/question")
@CrossOrigin(origins = "http://localhost:4200/")
public class QuestionController {
    @Autowired
    private IQuestionService questionService;

    @Autowired
    private ITopicService topicService;

    @GetMapping("/list")
    public ResponseEntity<List<Question>> showList() {
        List<Question> questions = questionService.findAllQuestion();
        if (questions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @GetMapping("/topic")
    public ResponseEntity<List<Topic>> listTopic() {
        List<Topic> topics = topicService.findAllTopic();
        if (topics.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(topics, HttpStatus.OK);
    }
    }
