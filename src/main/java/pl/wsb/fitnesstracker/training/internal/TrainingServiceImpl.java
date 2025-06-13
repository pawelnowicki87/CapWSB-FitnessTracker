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
 * Implementacja serwisu odpowiedzialnego za operacje na treningach.
 * Oferuje metody do tworzenia, aktualizacji, usuwania i pobierania treningów,
 * korzystając z {@link TrainingRepository} oraz {@link TrainingMapper}.
 */
@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService, TrainingProvider {

    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;

    /**
     * Zwraca wszystkie treningi z bazy danych.
     *
     * @return lista treningów w postaci DTO
     */
    @Override
    public List<TrainingDto> findAll() {
        return trainingRepository.findAll()
                .stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Zwraca wszystkie treningi przypisane do użytkownika o podanym ID.
     *
     * @param userId identyfikator użytkownika
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
     * Zwraca treningi o określonym typie aktywności.
     *
     * @param activityType typ aktywności (np. RUNNING, CYCLING)
     * @return lista treningów jako DTO
     */
    @Override
    public List<TrainingDto> findAllByActivityType(ActivityType activityType) {
        return trainingRepository.findByActivityType(activityType)
                .stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Zwraca treningi, które zakończyły się po określonej dacie.
     *
     * @param afterTime data graniczna (LocalDate)
     * @return lista treningów zakończonych po tej dacie w postaci DTO
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
     * Tworzy nowy trening na podstawie danych wejściowych i przypisanego użytkownika.
     *
     * @param body dane wejściowe treningu
     * @param user właściciel treningu
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
     * Aktualizuje istniejący trening na podstawie podanego ID i nowych danych.
     *
     * @param trainingId identyfikator treningu
     * @param body nowe dane treningu
     * @param user użytkownik powiązany z treningiem
     * @return zaktualizowany trening jako DTO
     * @throws IllegalArgumentException jeśli trening nie istnieje
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
     * Usuwa wszystkie treningi przypisane do użytkownika o wskazanym identyfikatorze.
     *
     * @param userId identyfikator użytkownika
     */
    @Override
    @Transactional
    public void deleteTrainingsByUserId(Long userId) {
        trainingRepository.deleteByUser_Id(userId);
    }

    /**
     * Zwraca użytkownika przypisanego do treningu o podanym ID.
     *
     * @param trainingId identyfikator treningu
     * @return {@link Optional} z użytkownikiem, jeśli trening istnieje
     */
    @Override
    public Optional<User> getTraining(Long trainingId) {
        return trainingRepository.findById(trainingId)
                .map(Training::getUser);
    }
}
