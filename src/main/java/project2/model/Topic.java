package project2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_topic")
    private Long idTopic;

    @Column(name = "name_topic")
    private String nameTopic;

    @OneToMany(mappedBy = "topic")
    @JsonBackReference(value = "topic_question")
    private List<Question> questionList;

    public Topic() {
    }

    public Topic(Long idTopic, String nameTopic, List<Question> questionList) {
        this.idTopic = idTopic;
        this.nameTopic = nameTopic;
        this.questionList = questionList;
    }

    public Long getIdTopic() {
        return idTopic;
    }

    public void setIdTopic(Long idTopic) {
        this.idTopic = idTopic;
    }

    public String getNameTopic() {
        return nameTopic;
    }

    public void setNameTopic(String nameTopic) {
        this.nameTopic = nameTopic;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }
}
