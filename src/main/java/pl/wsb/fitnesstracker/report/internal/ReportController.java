package pl.wsb.fitnesstracker.report.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller responsible for operations related to training reports.
 * Enables manual triggering of report generation through HTTP requests.
 */
@RestController
@RequestMapping("/v1/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportServiceImpl reportService;

    /**
     * Initiates the sending of monthly training summary reports.
     * This endpoint can be used for manual triggering, such as testing or admin tasks.
     *
     * @return ResponseEntity with a success confirmation.
     */
    @PostMapping("/monthly")
    public ResponseEntity<String> triggerMonthlyReport() {
        reportService.sendMonthlyReports();
        return ResponseEntity
                .ok("Monthly training reports have been successfully sent.");
    }
}
