package pl.wsb.fitnesstracker.report.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST API controller for report-related operations.
 * Allows triggering report generation via HTTP requests.
 */
@RestController
@RequestMapping("/v1/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportServiceImpl reportScheduler;

    /**
     * Endpoint to manually trigger sending monthly reports.
     *
     * @return confirmation message
     */
    @PostMapping("/monthly")
    public ResponseEntity<String> sendMonthlyReportNow() {
        reportScheduler.sendMonthlyReports();
        return ResponseEntity.ok("Monthly report sent.");
    }
}
