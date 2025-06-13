package pl.wsb.fitnesstracker.report.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.mail.api.EmailDto;
import pl.wsb.fitnesstracker.mail.api.EmailService;
import pl.wsb.fitnesstracker.report.api.ReportService;
import pl.wsb.fitnesstracker.training.api.TrainingDto;
import pl.wsb.fitnesstracker.training.api.TrainingService;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.internal.UserRepository;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for generating and emailing monthly training reports to users.
 *
 * <p>
 * On the first day of each month at 8:00 AM (UTC), this service collects training data
 * from the previous calendar month, summarizes it, and sends an email to each user
 * with their personal training overview.
 * </p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final UserRepository userRepository;
    private final TrainingService trainingService;
    private final EmailService emailService;

    /**
     * Sends personalized training summaries to all users for the previous month.
     * Scheduled to run automatically on the 1st of each month at 08:00 (UTC).
     */
    @Scheduled(cron = "0 0 8 1 * ?")
    public void sendMonthlyReports() {
        LocalDate startOfMonth = LocalDate.now(ZoneOffset.UTC).minusMonths(1).withDayOfMonth(1);
        LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());

        List<User> users = userRepository.findAll();

        for (User user : users) {
            List<TrainingDto> trainingsLastMonth = trainingService.findAllByUserId(user.getId()).stream()
                    .filter(training -> {
                        LocalDate endDate = toUtcDate(training.getEndTime());
                        return !endDate.isBefore(startOfMonth) && !endDate.isAfter(endOfMonth);
                    })
                    .collect(Collectors.toList());

            long totalTrainings = trainingsLastMonth.size();

            String trainingDates = trainingsLastMonth.stream()
                    .map(t -> toUtcDate(t.getEndTime()).toString())
                    .sorted()
                    .collect(Collectors.joining(", "));

            long totalMinutes = trainingsLastMonth.stream()
                    .mapToLong(t -> Duration.between(
                            toUtcDateTime(t.getStartTime()),
                            toUtcDateTime(t.getEndTime())
                    ).toMinutes())
                    .sum();

            String subject = "Your Monthly Training Summary";
            String content = String.format(
                    "Hello %s,\n\nIn %s, you completed %d training(s).\n\n" +
                            "Training Dates: %s\nTotal Time: %d minutes\n\n" +
                            "Keep it up!\n— Fitness Tracker Team",
                    user.getFirstName(),
                    startOfMonth.getMonth(),
                    totalTrainings,
                    trainingDates.isEmpty() ? "None" : trainingDates,
                    totalMinutes
            );

            emailService.send(new EmailDto(user.getEmail(), subject, content));
        }
    }

    private LocalDate toUtcDate(Date date) {
        return date.toInstant().atZone(ZoneOffset.UTC).toLocalDate();
    }

    private LocalDateTime toUtcDateTime(Date date) {
        return date.toInstant().atZone(ZoneOffset.UTC).toLocalDateTime();
    }
}

