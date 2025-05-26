package pl.wsb.fitnesstracker.report.api;

/**
 * Service interface for generating and sending training reports.
 */
public interface ReportService {
    /**
     * Sends monthly training summary reports to all users.
     * <p>
     * This method is typically scheduled to run automatically (e.g., once per month),
     * fetching training data from the previous month and sending summary emails.
     * </p>
     */
    void sendMonthlyReports();
}
