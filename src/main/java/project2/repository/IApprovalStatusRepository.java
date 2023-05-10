package project2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project2.model.ApprovalStatus;

import java.util.List;

@Repository
public interface IApprovalStatusRepository extends JpaRepository<ApprovalStatus, Long> {

}
