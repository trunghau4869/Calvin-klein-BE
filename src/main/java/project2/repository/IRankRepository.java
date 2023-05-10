package project2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project2.model.Rank;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRankRepository extends JpaRepository<Rank, Long> {

    @Query(value = "select * from `rank` where name_rank = ?1",nativeQuery = true)
    Optional<Rank> findByNameRank(String nameRank);
}
