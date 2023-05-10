package project2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project2.model.Question;

@Repository
public interface IQuestionRepository extends JpaRepository<Question, Long> {
}
