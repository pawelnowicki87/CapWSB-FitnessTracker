package pl.wsb.fitnesstracker.training.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import pl.wsb.fitnesstracker.training.api.Training;

import java.util.Date;
import java.util.List;

/**
 * Repozytorium JPA dla encji {@link Training}.
 * Udostępnia standardowe operacje CRUD oraz dodatkowe metody wyszukiwania treningów według użytkownika, typu aktywności i daty.
 */
public interface TrainingRepository extends JpaRepository<Training, Long> {

    /**
     * Usuwa wszystkie treningi powiązane z użytkownikiem o wskazanym identyfikatorze.
     * Operacja wykonywana w ramach transakcji.
     *
     * @param userId identyfikator użytkownika, którego treningi mają zostać usunięte
     */
    @Transactional
    void deleteByUser_Id(Long userId);

    /**
     * Zwraca wszystkie treningi przypisane do użytkownika o danym ID.
     *
     * @param userId identyfikator użytkownika
     * @return lista treningów należących do użytkownika
     */
    List<Training> findByUser_Id(Long userId);

    /**
     * Zwraca wszystkie treningi o określonym typie aktywności.
     *
     * @param activityType typ aktywności (np. RUNNING, SWIMMING)
     * @return lista treningów zgodnych z danym typem aktywności
     */
    List<Training> findByActivityType(ActivityType activityType);

    /**
     * Zwraca treningi, które zakończyły się po wskazanej dacie.
     *
     * @param endTime data graniczna zakończenia treningu
     * @return lista treningów zakończonych po dacie {@code endTime}
     */
    List<Training> findByEndTimeAfter(Date endTime);
}
