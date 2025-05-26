package pl.wsb.fitnesstracker.mail.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.wsb.fitnesstracker.mail.api.EmailDto;
import pl.wsb.fitnesstracker.mail.api.EmailService;

/**
 * REST controller for handling email-related operations.
 * Provides endpoints to send emails, including a test email endpoint.
 */
@RestController
@RequestMapping("/v1/mails")
@RequiredArgsConstructor
public class MailController {

    private final EmailService emailService;

    /**
     * Endpoint to send a test email to a specified recipient.
     *
     * @param to The email address to which the test email will be sent.
     * @return ResponseEntity with a success message indicating that the test email was sent.
     */
    @PostMapping("/send-test")
    public ResponseEntity<String> sendTestEmail(@RequestParam String to) {
        String subject = "Test Email from Fitness Tracker";
        String content = "This is a test email sent to verify the email sending functionality.";
        EmailDto emailDto = new EmailDto(to, subject, content);

        emailService.send(emailDto);

        return ResponseEntity.ok("Test email sent to " + to);
    }
}
