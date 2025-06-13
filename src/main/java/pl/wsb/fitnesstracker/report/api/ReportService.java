package pl.wsb.fitnesstracker.report.api;

/**
 * Service interface for handling user training report distribution.
 */
public interface ReportService {

    /**
     * Sends a summary report of user training activity for the previous month.
     * <p>
     * Intended to be executed on a scheduled basis, this method collects training data
     * from the last calendar month and emails a personalized report to each user.
     * </p>
     */
    void sendMonthlyReports();
}
