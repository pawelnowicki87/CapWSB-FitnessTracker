package pl.wsb.fitnesstracker.training.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.training.api.TrainingDto;
import pl.wsb.fitnesstracker.training.api.TrainingRequestBody;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.internal.UserRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Kontroler REST odpowiadający za operacje na treningach użytkowników.
 * Udostępnia metody do tworzenia, aktualizacji i filtrowania treningów po różnych kryteriach.
 */
@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingServiceImpl trainingService;
    private final UserRepository userRepository;

    /**
     * Zwraca wszystkie treningi zapisane w systemie.
     *
     * @return odpowiedź HTTP 200 z listą treningów
     */
    @GetMapping
    public ResponseEntity<List<TrainingDto>> getAll() {
        List<TrainingDto> trainings = trainingService.findAll();
        return ResponseEntity.ok(trainings);
    }

    /**
     * Zwraca wszystkie treningi przypisane do danego użytkownika.
     *
     * @param userId identyfikator użytkownika
     * @return odpowiedź HTTP 200 z listą treningów użytkownika
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<TrainingDto>> getAllByUser(@PathVariable Long userId) {
        List<TrainingDto> trainings = trainingService.findAllByUserId(userId);
        return ResponseEntity.ok(trainings);
    }

    /**
     * Zwraca treningi odpowiadające określonemu typowi aktywności.
     *
     * @param activityType typ aktywności (np. RUNNING, CYCLING)
     * @return odpowiedź HTTP 200 z listą dopasowanych treningów
     */
    @GetMapping("/activityType")
    public ResponseEntity<List<TrainingDto>> getAllByActivityType(@RequestParam ActivityType activityType) {
        List<TrainingDto> trainings = trainingService.findAllByActivityType(activityType);
        return ResponseEntity.ok(trainings);
    }

    /**
     * Zwraca treningi zakończone po określonej dacie.
     *
     * @param afterTime data w formacie YYYY-MM-DD
     * @return odpowiedź HTTP 200 z listą treningów zakończonych po podanej dacie
     */
    @GetMapping("/finished/{afterTime}")
    public ResponseEntity<List<TrainingDto>> getFinishedAfter(@PathVariable LocalDate afterTime) {
        List<TrainingDto> trainings = trainingService.findAllFinishedAfter(afterTime);
        return ResponseEntity.ok(trainings);
    }

    /**
     * Tworzy nowy trening na podstawie danych przesłanych w żądaniu.
     * Użytkownik musi istnieć w bazie danych.
     *
     * @param body dane treningu
     * @return odpowiedź HTTP 201 z utworzonym treningiem
     * @throws IllegalArgumentException jeśli użytkownik nie istnieje
     */
    @PostMapping
    public ResponseEntity<TrainingDto> create(@RequestBody TrainingRequestBody body) {
        User user = userRepository.findById(body.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono użytkownika o ID: " + body.getUserId()));

        TrainingDto created = trainingService.create(body, user);
        return ResponseEntity.status(201).body(created);
    }

    /**
     * Aktualizuje istniejący trening o podanym ID.
     * Użytkownik powiązany z treningiem musi istnieć.
     *
     * @param trainingId identyfikator treningu do aktualizacji
     * @param body nowe dane treningu
     * @return odpowiedź HTTP 200 z zaktualizowanym treningiem
     * @throws IllegalArgumentException jeśli użytkownik nie istnieje
     */
    @PutMapping("/{trainingId}")
    public ResponseEntity<TrainingDto> update(@PathVariable Long trainingId, @RequestBody TrainingRequestBody body) {
        User user = userRepository.findById(body.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono użytkownika o ID: " + body.getUserId()));

        TrainingDto updated = trainingService.update(trainingId, body, user);
        return ResponseEntity.ok(updated);
    }

}
