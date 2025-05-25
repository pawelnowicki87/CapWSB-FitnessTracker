package pl.wsb.fitnesstracker.training.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.training.api.TrainingRequestBody;
import pl.wsb.fitnesstracker.training.api.TrainingDto;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.internal.UserRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * REST API kontroler dla operacji na użytkownikach.
 * Umożliwia operacje CRUD i wyszukiwanie po emailu i wieku.
 */
@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingServiceImpl trainingService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<TrainingDto>> getAll() {
        return ResponseEntity.ok(trainingService.findAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<TrainingDto>> getAllByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(trainingService.findAllByUserId(userId));
    }

    @GetMapping("/activityType")
    public ResponseEntity<List<TrainingDto>> getAllByActivityType(@RequestParam ActivityType activityType) {
        return ResponseEntity.ok(trainingService.findAllByActivityType(activityType));
    }

    @GetMapping("/finished/{afterTime}")
    public ResponseEntity<List<TrainingDto>> getFinishedAfter(@PathVariable LocalDate afterTime) {
        return ResponseEntity.ok(trainingService.findAllFinishedAfter(afterTime));
    }

    @PostMapping
    public ResponseEntity<TrainingDto> create(@RequestBody TrainingRequestBody body) {
        User user = userRepository.findById(body.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        TrainingDto createdTraining = trainingService.create(body, user);
        return ResponseEntity.status(201).body(createdTraining);
    }

    @PutMapping("/{trainingId}")
    public ResponseEntity<TrainingDto> update(@PathVariable Long trainingId, @RequestBody TrainingRequestBody body) {
        User user = userRepository.findById(body.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return ResponseEntity.ok(trainingService.update(trainingId, body, user));
    }

}
