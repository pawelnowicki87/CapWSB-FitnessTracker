package pl.wsb.fitnesstracker.training.api;

import pl.wsb.fitnesstracker.training.internal.ActivityType;
import pl.wsb.fitnesstracker.user.api.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TrainingService {

    /**
     * Pobiera wszystkie treningi.
     */
    List<TrainingDto> findAll();

    /**
     * Pobiera treningi dla konkretnego użytkownika.
     */
    List<TrainingDto> findAllByUserId(Long userId);

    /**
     * Pobiera treningi o określonym typie aktywności.
     */
    List<TrainingDto> findAllByActivityType(ActivityType activityType);

    /**
     * Pobiera wszystkie zakończone treningi po dacie.
     */
    List<TrainingDto> findAllFinishedAfter(LocalDate afterTime);

    /**
     * Tworzy nowy trening na podstawie DTO i użytkownika.
     */
    TrainingDto create(TrainingRequestBody body, User user);

    /**
     * Aktualizuje istniejący trening o danym ID.
     */
    TrainingDto update(Long trainingId, TrainingRequestBody body, User user);

    /**
     * Usuwa wszystkie treningi danego użytkownika.
     */
    void deleteTrainingsByUserId(Long userId);

    /**
     * (Z interfejsu TrainingProvider)
     * Zwraca trening po ID, opcjonalnie.
     */
    Optional<User> getTraining(Long trainingId);
}
