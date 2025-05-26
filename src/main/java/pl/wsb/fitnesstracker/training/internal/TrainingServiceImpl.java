package pl.wsb.fitnesstracker.training.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.wsb.fitnesstracker.training.api.*;
import pl.wsb.fitnesstracker.user.api.User;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementacja serwisu zarządzającego treningami.
 * Udostępnia metody do wyszukiwania, tworzenia, aktualizacji i usuwania treningów.
 * Wykorzystuje {@link TrainingRepository} do operacji na bazie danych
 * oraz {@link TrainingMapper} do konwersji między encjami a DTO.
 */
@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService, TrainingProvider {

    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;

    /**
     * Pobiera wszystkie treningi dostępne w systemie.
     *
     * @return lista wszystkich treningów jako DTO
     */
    @Override
    public List<TrainingDto> findAll() {
        return trainingRepository.findAll()
                .stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Pobiera treningi przypisane do użytkownika o podanym ID.
     *
     * @param userId ID użytkownika
     * @return lista treningów danego użytkownika jako DTO
     */
    @Override
    public List<TrainingDto> findAllByUserId(Long userId) {
        return trainingRepository.findByUser_Id(userId)
                .stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Pobiera treningi o określonym typie aktywności.
     *
     * @param activityType typ aktywności
     * @return lista treningów o danym typie jako DTO
     */
    @Override
    public List<TrainingDto> findAllByActivityType(ActivityType activityType) {
        return trainingRepository.findByActivityType(activityType)
                .stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Pobiera treningi zakończone po określonej dacie.
     *
     * @param afterTime data zakończenia po której treningi mają być zwrócone
     * @return lista treningów zakończonych po podanej dacie jako DTO
     */
    @Override
    public List<TrainingDto> findAllFinishedAfter(LocalDate afterTime) {
        Date afterDate = Date.from(afterTime.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return trainingRepository.findByEndTimeAfter(afterDate)
                .stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Tworzy nowy trening na podstawie przesłanego ciała żądania oraz użytkownika.
     *
     * @param body dane treningu do utworzenia
     * @param user użytkownik, do którego przypisany jest trening
     * @return utworzony trening jako DTO
     */
    @Override
    @Transactional
    public TrainingDto create(TrainingRequestBody body, User user) {
        Training training = trainingMapper.toEntity(body, user);
        Training saved = trainingRepository.save(training);
        return trainingMapper.toDto(saved);
    }

    /**
     * Aktualizuje istniejący trening o podanym ID na podstawie przesłanych danych i użytkownika.
     *
     * @param trainingId ID treningu do aktualizacji
     * @param body nowe dane treningu
     * @param user użytkownik powiązany z treningiem
     * @return zaktualizowany trening jako DTO
     * @throws IllegalArgumentException jeśli trening o podanym ID nie istnieje
     */
    @Override
    @Transactional
    public TrainingDto update(Long trainingId, TrainingRequestBody body, User user) {
        Training existing = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new IllegalArgumentException("Training not found"));

        existing.setUser(user);
        existing.setStartTime(body.getStartTime());
        existing.setEndTime(body.getEndTime());
        existing.setActivityType(body.getActivityType());
        existing.setDistance(body.getDistance());
        existing.setAverageSpeed(body.getAverageSpeed());

        Training updated = trainingRepository.save(existing);
        return trainingMapper.toDto(updated);
    }

    /**
     * Usuwa wszystkie treningi przypisane do użytkownika o podanym ID.
     *
     * @param userId ID użytkownika, którego treningi mają zostać usunięte
     */
    @Override
    @Transactional
    public void deleteTrainingsByUserId(Long userId) {
        trainingRepository.deleteByUser_Id(userId);
    }

    /**
     * Pobiera użytkownika powiązanego z treningiem o podanym ID.
     *
     * @param trainingId ID treningu
     * @return Optional zawierający użytkownika lub pusty, jeśli trening nie istnieje
     */
    @Override
    public Optional<User> getTraining(Long trainingId) {
        return trainingRepository.findById(trainingId)
                .map(Training::getUser);
    }
}
