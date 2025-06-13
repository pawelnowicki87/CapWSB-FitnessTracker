package pl.wsb.fitnesstracker.training.api;

import pl.wsb.fitnesstracker.training.internal.ActivityType;
import pl.wsb.fitnesstracker.user.api.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Interfejs serwisu odpowiedzialnego za zarządzanie operacjami treningowymi.
 * Udostępnia metody umożliwiające tworzenie, modyfikowanie, usuwanie oraz wyszukiwanie treningów.
 */
public interface TrainingService {

    /**
     * Zwraca listę wszystkich treningów dostępnych w systemie.
     *
     * @return lista obiektów {@link TrainingDto} reprezentujących treningi
     */
    List<TrainingDto> findAll();

    /**
     * Zwraca listę treningów przypisanych do użytkownika o podanym ID.
     *
     * @param userId unikalny identyfikator użytkownika
     * @return lista treningów przypisanych do użytkownika
     */
    List<TrainingDto> findAllByUserId(Long userId);

    /**
     * Zwraca listę treningów przypisanych do danego typu aktywności.
     *
     * @param activityType typ aktywności {@link ActivityType}
     * @return lista treningów o wskazanym typie aktywności
     */
    List<TrainingDto> findAllByActivityType(ActivityType activityType);

    /**
     * Zwraca treningi, które zakończyły się po określonej dacie.
     *
     * @param afterTime data, po której treningi powinny się kończyć
     * @return lista treningów zakończonych po podanej dacie
     */
    List<TrainingDto> findAllFinishedAfter(LocalDate afterTime);

    /**
     * Tworzy nowy trening na podstawie danych wejściowych oraz przypisanego użytkownika.
     *
     * @param body dane treningu przekazane przez klienta
     * @param user użytkownik, do którego przypisany jest trening
     * @return nowo utworzony trening w postaci {@link TrainingDto}
     */
    TrainingDto create(TrainingRequestBody body, User user);

    /**
     * Aktualizuje istniejący trening na podstawie przekazanych danych.
     *
     * @param trainingId identyfikator aktualizowanego treningu
     * @param body nowe dane treningu
     * @param user użytkownik powiązany z treningiem
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
     * Wyszukuje trening po identyfikatorze i zwraca go, jeśli istnieje.
     *
     * @param trainingId identyfikator treningu
     * @return {@link Optional} zawierający trening, jeśli został znaleziony
     */
    Optional<User> getTraining(Long trainingId);
}
