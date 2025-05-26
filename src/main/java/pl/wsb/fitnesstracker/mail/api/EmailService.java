package pl.wsb.fitnesstracker.mail.api;

/**
 * Service interface for sending emails.
 */
public interface EmailService {
    /**
     * Sends an email based on the provided email data transfer object.
     *
     * @param emailDto the email data including recipient, subject, and content
     */
    void send(EmailDto emailDto);
}
