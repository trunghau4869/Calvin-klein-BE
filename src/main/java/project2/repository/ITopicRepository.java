package project2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project2.model.Topic;

@Repository
public interface ITopicRepository extends JpaRepository<Topic, Long> {
}
