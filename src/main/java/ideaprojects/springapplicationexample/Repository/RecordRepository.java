package ideaprojects.springapplicationexample.Repository;

import ideaprojects.springapplicationexample.entity.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ideaprojects.springapplicationexample.entity.Record;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Integer> {
    @Modifying
    @Query("update Record set status = :status where id = :id")
    void update(int id, @Param("status") RecordStatus newStatus);


    List<Record> findAllByStatus(RecordStatus status);

    List<Record> findAllByStatusAndTitleContainsOrderByIdDesc(RecordStatus status, String title);
}