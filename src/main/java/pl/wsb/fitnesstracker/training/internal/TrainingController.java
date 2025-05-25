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
 * Kontroler REST API zarządzający operacjami na treningach.
 * Umożliwia wykonywanie operacji CRUD oraz filtrowanie treningów po różnych kryteriach.
 */
@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingServiceImpl trainingService;
    private final UserRepository userRepository;

    /**
     * Pobiera wszystkie dostępne treningi.
     *
     * @return odpowiedź HTTP z listą wszystkich treningów w ciele
     */
    @GetMapping
    public ResponseEntity<List<TrainingDto>> getAll() {
        return ResponseEntity.ok(trainingService.findAll());
    }

    /**
     * Pobiera wszystkie treningi przypisane do użytkownika o podanym identyfikatorze.
     *
     * @param userId identyfikator użytkownika
     * @return odpowiedź HTTP z listą treningów użytkownika w ciele
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<TrainingDto>> getAllByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(trainingService.findAllByUserId(userId));
    }

    /**
     * Pobiera treningi o określonym typie aktywności.
     *
     * @param activityType typ aktywności (np. RUNNING, CYCLING)
     * @return odpowiedź HTTP z listą treningów spełniających kryterium
     */
    @GetMapping("/activityType")
    public ResponseEntity<List<TrainingDto>> getAllByActivityType(@RequestParam ActivityType activityType) {
        return ResponseEntity.ok(trainingService.findAllByActivityType(activityType));
    }

    /**
     * Pobiera treningi, które zakończyły się po podanej dacie.
     *
     * @param afterTime data w formacie ISO (YYYY-MM-DD)
     * @return odpowiedź HTTP z listą treningów zakończonych po dacie
     */
    @GetMapping("/finished/{afterTime}")
    public ResponseEntity<List<TrainingDto>> getFinishedAfter(@PathVariable LocalDate afterTime) {
        return ResponseEntity.ok(trainingService.findAllFinishedAfter(afterTime));
    }

    /**
     * Tworzy nowy trening na podstawie danych przesłanych w żądaniu.
     * Użytkownik musi istnieć w bazie danych.
     *
     * @param body obiekt zawierający dane treningu
     * @return odpowiedź HTTP z utworzonym treningiem oraz kodem 201 (Created)
     * @throws IllegalArgumentException gdy użytkownik o podanym ID nie istnieje
     */
    @PostMapping
    public ResponseEntity<TrainingDto> create(@RequestBody TrainingRequestBody body) {
        User user = userRepository.findById(body.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        TrainingDto createdTraining = trainingService.create(body, user);
        return ResponseEntity.status(201).body(createdTraining);
    }

    /**
     * Aktualizuje istniejący trening o wskazanym ID.
     * Użytkownik musi istnieć w bazie danych.
     *
     * @param trainingId identyfikator treningu do aktualizacji
     * @param body nowe dane treningu
     * @return odpowiedź HTTP z zaktualizowanym treningiem
     * @throws IllegalArgumentException gdy użytkownik o podanym ID nie istnieje
     */
    @PutMapping("/{trainingId}")
    public ResponseEntity<TrainingDto> update(@PathVariable Long trainingId, @RequestBody TrainingRequestBody body) {
        User user = userRepository.findById(body.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return ResponseEntity.ok(trainingService.update(trainingId, body, user));
    }

}
