package project2.model;

import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_question")
    private Long idQuestion;


    @Column(name = "content")
    private String Content;

    @Column(name = "code")
    private String code;

    @Column(name = "answer")
    private String answer;

    @ManyToOne(targetEntity = Topic.class)
    @JoinColumn(name = "id_topic",nullable = false)
    private Topic topic;

    public Question() {
    }

    public Question(Long idQuestion, String content, String code, String answer, Topic topic) {
        this.idQuestion = idQuestion;
        Content = content;
        this.code = code;
        this.answer = answer;
        this.topic = topic;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(Long idQuestion) {
        this.idQuestion = idQuestion;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}
