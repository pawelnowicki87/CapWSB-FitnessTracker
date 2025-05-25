package pl.wsb.fitnesstracker.training.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import pl.wsb.fitnesstracker.training.api.Training;

import java.util.Date;
import java.util.List;

/**
 * Repository interface for {@link Training} entities.
 * Provides CRUD operations and custom query methods related to trainings.
 */
public interface TrainingRepository extends JpaRepository<Training, Long> {
    /**
     * Deletes all trainings associated with a specific user ID.
     * This operation is transactional to ensure data integrity.
     *
     * @param userId the ID of the user whose trainings should be deleted
     */
    @Transactional
    void deleteByUser_Id(Long userId);

    // Finds trainings by userId directly via query (Spring Data magic)
    List<Training> findByUser_Id(Long userId);

    // Finds trainings by activity type (direct DB query)
    List<Training> findByActivityType(ActivityType activityType);

    // Finds trainings finished after the given date (direct DB query)
    List<Training> findByEndTimeAfter(Date endTime);
}
