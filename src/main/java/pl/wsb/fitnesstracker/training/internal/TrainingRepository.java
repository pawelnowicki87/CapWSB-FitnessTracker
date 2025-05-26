package pl.wsb.fitnesstracker.training.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.internal.ActivityType;

import java.util.Date;
import java.util.List;

/**
 * Interfejs repozytorium dla encji {@link Training}.
 * Zapewnia podstawowe operacje CRUD oraz niestandardowe metody zapytań dotyczące treningów.
 */
public interface TrainingRepository extends JpaRepository<Training, Long> {
    /**
     * Usuwa wszystkie treningi powiązane z użytkownikiem o podanym ID.
     * Operacja oznaczona jako transakcyjna, aby zapewnić integralność danych.
     *
     * @param userId ID użytkownika, którego treningi mają zostać usunięte
     */
    @Transactional
    void deleteByUser_Id(Long userId);

    /**
     * Znajduje listę treningów przypisanych do użytkownika o podanym ID.
     *
     * @param userId ID użytkownika
     * @return lista treningów użytkownika
     */
    List<Training> findByUser_Id(Long userId);

    /**
     * Znajduje treningi o określonym typie aktywności.
     *
     * @param activityType typ aktywności treningu
     * @return lista treningów o danym typie aktywności
     */
    List<Training> findByActivityType(ActivityType activityType);

    /**
     * Znajduje treningi, które zakończyły się po podanej dacie.
     *
     * @param endTime data końca treningu
     * @return lista treningów zakończonych po dacie endTime
     */
    List<Training> findByEndTimeAfter(Date endTime);
}
