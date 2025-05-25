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

@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService, TrainingProvider {

    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;

    @Override
    public List<TrainingDto> findAll() {
        return trainingRepository.findAll()
                .stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TrainingDto> findAllByUserId(Long userId) {
        return trainingRepository.findByUser_Id(userId)
                .stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TrainingDto> findAllByActivityType(ActivityType activityType) {
        return trainingRepository.findByActivityType(activityType)
                .stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TrainingDto> findAllFinishedAfter(LocalDate afterTime) {
        Date afterDate = Date.from(afterTime.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return trainingRepository.findByEndTimeAfter(afterDate)
                .stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TrainingDto create(TrainingRequestBody body, User user) {
        Training training = trainingMapper.toEntity(body, user);
        Training saved = trainingRepository.save(training);
        return trainingMapper.toDto(saved);
    }

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

    @Override
    @Transactional
    public void deleteTrainingsByUserId(Long userId) {
        trainingRepository.deleteByUser_Id(userId);
    }

    @Override
    public Optional<User> getTraining(Long trainingId) {
        return trainingRepository.findById(trainingId)
                .map(Training::getUser);
    }
}
