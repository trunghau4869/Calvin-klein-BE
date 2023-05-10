package project2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project2.model.Topic;
import project2.repository.ITopicRepository;
import project2.service.ITopicService;

import java.util.List;

@Service
public class TopicService implements ITopicService {
    @Autowired
    private ITopicRepository topicRepository;

    @Override
    public List<Topic> findAllTopic() {
        return topicRepository.findAll();
    }
}
