package project2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project2.model.Transport;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

@Repository
public interface ITransportRepository extends JpaRepository<Transport, Long> {
    @Query(value = "select * from Transport",nativeQuery = true)
    List<Transport> getAll();
}
