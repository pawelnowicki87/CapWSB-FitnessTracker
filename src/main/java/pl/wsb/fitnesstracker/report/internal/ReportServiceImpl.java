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
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link ReportService} interface responsible for
 * generating and sending monthly training summary reports to users.
 * <p>
 * This service fetches all users and their training sessions from the
 * previous month, calculates summary statistics such as total trainings,
 * training dates, and total training time, and then sends these summaries
 * via email to each user.
 * </p>
 * <p>
 * The report sending is scheduled to run automatically at 8:00 AM on the
 * first day of each month.
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
     * Scheduled method that runs at 8:00 AM on the first day of each month
     * to send monthly training summary reports to all users.
     * <p>
     * The method calculates the date range for the previous month and retrieves
     * all training sessions for each user within that period. It then computes
     * the number of trainings, the training dates, and the total training time
     * in minutes. This information is formatted into an email and sent to
     * each user's registered email address.
     * </p>
     */
    @Scheduled(cron = "0 0 8 1 * ?")
    public void sendMonthlyReports() {
        LocalDate startOfMonth = LocalDate.now(ZoneOffset.UTC).minusMonths(1).withDayOfMonth(1);
        LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());

        List<User> users = userRepository.findAll();

        for (User user : users) {
            List<TrainingDto> userTrainings = trainingService.findAllByUserId(user.getId()).stream()
                    .filter(t -> {
                        LocalDate endDate = t.getEndTime().toInstant()
                                .atZone(ZoneOffset.UTC)
                                .toLocalDate();
                        return !endDate.isBefore(startOfMonth) && !endDate.isAfter(endOfMonth);
                    })
                    .collect(Collectors.toList());

            long count = userTrainings.size();

            String trainingDates = userTrainings.stream()
                    .map(t -> t.getEndTime().toInstant()
                            .atZone(ZoneOffset.UTC)
                            .toLocalDate().toString())
                    .sorted()
                    .collect(Collectors.joining(", "));

            long totalMinutes = userTrainings.stream()
                    .mapToLong(t -> Duration.between(
                            t.getStartTime().toInstant().atZone(ZoneOffset.UTC).toLocalDateTime(),
                            t.getEndTime().toInstant().atZone(ZoneOffset.UTC).toLocalDateTime()
                    ).toMinutes())
                    .sum();

            String subject = "Monthly Training Summary";
            String content = String.format(
                    "Hello %s,\n\nIn %s you completed %d training(s).\n\n" +
                            "Training Dates: %s\nTotal Time: %d minutes\n\n" +
                            "Keep up the great work!\nFitness Tracker",
                    user.getFirstName(),
                    startOfMonth.getMonth(),
                    count,
                    trainingDates.isEmpty() ? "None" : trainingDates,
                    totalMinutes
            );

            emailService.send(new EmailDto(user.getEmail(), subject, content));
        }
    }
}
