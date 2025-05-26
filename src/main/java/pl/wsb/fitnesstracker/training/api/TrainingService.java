package pl.wsb.fitnesstracker.training.api;

import pl.wsb.fitnesstracker.training.internal.ActivityType;
import pl.wsb.fitnesstracker.user.api.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
/**
 * Interfejs serwisu zarządzającego operacjami na treningach.
 * Definiuje podstawowe metody CRUD oraz wyszukiwania treningów.
 */
public interface TrainingService {

    /**
     * Pobiera listę wszystkich dostępnych treningów.
     *
     * @return lista obiektów {@link TrainingDto} reprezentujących treningi
     */
    List<TrainingDto> findAll();

    /**
     * Pobiera wszystkie treningi przypisane do użytkownika o podanym identyfikatorze.
     *
     * @param userId identyfikator użytkownika
     * @return lista treningów użytkownika
     */
    List<TrainingDto> findAllByUserId(Long userId);

    /**
     * Pobiera listę treningów o wskazanym typie aktywności.
     *
     * @param activityType typ aktywności {@link ActivityType}
     * @return lista treningów z określonym typem aktywności
     */
    List<TrainingDto> findAllByActivityType(ActivityType activityType);

    /**
     * Pobiera wszystkie treningi, które zostały zakończone po wskazanej dacie.
     *
     * @param afterTime data po której treningi zostały zakończone
     * @return lista treningów zakończonych po podanej dacie
     */
    List<TrainingDto> findAllFinishedAfter(LocalDate afterTime);

    /**
     * Tworzy nowy trening na podstawie danych przesłanych w ciele żądania i przypisanego użytkownika.
     *
     * @param body obiekt {@link TrainingRequestBody} zawierający dane treningu
     * @param user użytkownik przypisany do treningu
     * @return utworzony trening jako {@link TrainingDto}
     */
    TrainingDto create(TrainingRequestBody body, User user);

    /**
     * Aktualizuje istniejący trening o podanym identyfikatorze, na podstawie nowych danych i użytkownika.
     *
     * @param trainingId identyfikator treningu do aktualizacji
     * @param body nowe dane treningu w postaci {@link TrainingRequestBody}
     * @param user użytkownik przypisany do treningu
     * @return zaktualizowany trening jako {@link TrainingDto}
     */
    TrainingDto update(Long trainingId, TrainingRequestBody body, User user);

    /**
     * Usuwa wszystkie treningi przypisane do użytkownika o podanym identyfikatorze.
     *
     * @param userId identyfikator użytkownika, którego treningi mają zostać usunięte
     */
    void deleteTrainingsByUserId(Long userId);

    /**
     * (Z interfejsu TrainingProvider)
     * Pobiera opcjonalnie trening o podanym identyfikatorze.
     *
     * @param trainingId identyfikator treningu
     * @return opcjonalny obiekt treningu {@link User}
     */
    Optional<User> getTraining(Long trainingId);
}
