package project2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project2.model.BiddingStatus;

@Repository
public interface IBiddingStatusRepository extends JpaRepository<BiddingStatus, Long> {
}
